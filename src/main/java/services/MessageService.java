
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository
	@Autowired
	private MessageRepository			messageRepository;

	// Supporting services
	@Autowired
	private ActorService				actorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;

	@Autowired
	private BoxService					boxService;


	// Simple CRUD methods
	// R9.3
	public Message create() {
		Message result;

		final Actor sender = this.actorService.findActorLogged();
		Assert.notNull(sender);
		Assert.isTrue(!sender.getIsSuspicious());

		result = new Message();
		final Collection<Actor> recipients = new HashSet<>();
		final Date moment = new Date(System.currentTimeMillis() - 1);

		result.setRecipients(recipients);
		result.setSender(sender);
		result.setMoment(moment);
		result.setFlagSpam(false);

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = this.messageRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Message findOne(final int messageId) {
		Assert.isTrue(messageId != 0);

		Message result;

		result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

	// R9.3
	public Message save(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() == 0); //Un mensaje no tiene sentido que se edite, por lo que sólo vendrá del create

		Message result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());

		final Actor sender = message.getSender();
		Assert.isTrue(sender.equals(actorLogged));

		// R17
		final Collection<String> spamWords = this.systemConfigurationService.getConfiguration().getSpamWords();
		for (final String sw : spamWords) {
			final Boolean flagSpam = (message.getBody().toLowerCase().contains(sw) || message.getSubject().toLowerCase().contains(sw)) ? true : false;
			if (flagSpam) {
				message.setFlagSpam(flagSpam);
				break;
			}
		}

		final Date moment = new Date(System.currentTimeMillis() - 1);
		message.setMoment(moment);

		result = this.messageRepository.save(message);

		final Box outBox = this.boxService.findOutBoxByActorId(result.getSender().getId());
		Assert.notNull(outBox);
		final Collection<Message> messagesOutBox = outBox.getMessages();
		messagesOutBox.add(result);
		outBox.setMessages(messagesOutBox);
		this.boxService.saveForSystemBox(outBox);

		for (final Actor recipient : result.getRecipients())
			if (result.getFlagSpam()) {
				final Box spamBox = this.boxService.findSpamBoxByActorId(recipient.getId());
				Assert.notNull(spamBox);
				final Collection<Message> messagesSpamBox = spamBox.getMessages();
				messagesSpamBox.add(result);
				spamBox.setMessages(messagesSpamBox);
				this.boxService.saveForSystemBox(spamBox);
			} else {
				final Box inBox = this.boxService.findInBoxByActorId(recipient.getId());
				Assert.notNull(inBox);
				final Collection<Message> messagesInBox = inBox.getMessages();
				messagesInBox.add(result);
				inBox.setMessages(messagesInBox);
				this.boxService.saveForSystemBox(inBox);
			}

		return result;
	}

	// R9.3
	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(this.messageRepository.exists(message.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());

		//R2: Note that a message may be stored in several boxes
		//and, but the system must keep a unique copy; removing a message from the "trash box"
		//removes it from every other box.

		final Collection<Message> messagesActorLogged = this.findMessagesByActorId(actorLogged.getId());
		Assert.isTrue(messagesActorLogged.contains(message));

		final Box trashBox = this.boxService.findTrashBoxByActorId(actorLogged.getId());
		Assert.notNull(trashBox);

		final Collection<Message> messagesTrashBox = trashBox.getMessages();

		if (messagesTrashBox.contains(message)) {
			messagesTrashBox.remove(message);
			trashBox.setMessages(messagesTrashBox);
			this.boxService.saveForSystemBox(trashBox);
			final Collection<Box> boxesMessage = this.boxService.findBoxesByMessageId(message.getId());
			if (boxesMessage.isEmpty() || boxesMessage == null)
				this.messageRepository.delete(message);
		} else {
			Collection<Message> messages = new HashSet<>();
			for (final Box b : actorLogged.getBoxes()) {
				messages = b.getMessages();
				messages.remove(message);
				b.setMessages(messages);
				this.boxService.saveForSystemBox(b);

			}
			messagesTrashBox.add(message);
			trashBox.setMessages(messagesTrashBox);
			this.boxService.saveForSystemBox(trashBox);
		}
	}

	// Other business methods
	public Collection<Message> findMessagesByActorId(final int actorId) {
		Assert.isTrue(actorId != 0);

		Collection<Message> result;

		result = this.messageRepository.findMessagesByActorId(actorId);
		return result;
	}

	// R9.3
	public Message saveSystem(final Message message) {
		System.out.println("aqui");
		Assert.notNull(message);
		System.out.println("ahi");
		Assert.isTrue(message.getId() == 0); //Un mensaje no tiene sentido que se edite, por lo que sólo vendrá del create

		Message result;

		final Actor sender = this.actorService.getSystemActor(); //Suponemos que el actor que envia el mensaje es un administrador "system"
		final Date moment = new Date(System.currentTimeMillis() - 1);
		message.setMoment(moment);
		message.setPriority("HIGH");
		message.setSender(sender);

		// R.17
		final Collection<String> spamWords = this.systemConfigurationService.getConfiguration().getSpamWords();
		for (final String sw : spamWords) {
			final Boolean flagSpam = (message.getBody().toLowerCase().contains(sw) || message.getSubject().toLowerCase().contains(sw)) ? true : false;
			if (flagSpam) {
				message.setFlagSpam(flagSpam);
				break;
			}
		}

		result = this.messageRepository.save(message);

		final Box outBox = this.boxService.findOutBoxByActorId(result.getSender().getId());
		System.out.println("??");
		Assert.notNull(outBox);
		System.out.println("try");
		final Collection<Message> messagesOutBox = outBox.getMessages();
		messagesOutBox.add(result);
		outBox.setMessages(messagesOutBox);
		this.boxService.saveForSystemBox(outBox);

		for (final Actor recipient : result.getRecipients())
			if (result.getFlagSpam()) {
				final Box spamBox = this.boxService.findSpamBoxByActorId(recipient.getId());
				Assert.notNull(spamBox);
				final Collection<Message> messagesSpamBox = spamBox.getMessages();
				messagesSpamBox.add(result);
				spamBox.setMessages(messagesSpamBox);
				this.boxService.saveForSystemBox(spamBox);
			} else {
				final Box inBox = this.boxService.findInBoxByActorId(recipient.getId());
				Assert.notNull(inBox);
				final Collection<Message> messagesInBox = inBox.getMessages();
				messagesInBox.add(result);
				inBox.setMessages(messagesInBox);
				this.boxService.saveForSystemBox(inBox);
			}

		return result;
	}

	// R12.4
	public Message broadcastMessage(final Message message) {
		Message result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final Collection<Actor> actorsSystem = this.actorService.findAll();
		actorsSystem.remove(actorLogged);
		message.setRecipients(actorsSystem);
		result = this.save(message);

		return result;
	}

	public void saveToMove(final Message message, final Box box) {
		Assert.notNull(message);
		Assert.notNull(box);

		final Box currentBox = this.boxService.getBoxFromMessageIdActorLogged(message.getId());
		final Collection<Message> currentBoxMessages = currentBox.getMessages();
		currentBoxMessages.remove(message);
		currentBox.setMessages(currentBoxMessages);
		this.boxService.saveForSystemBox(currentBox);
		final Collection<Message> boxMessages = box.getMessages();
		boxMessages.add(message);
		box.setMessages(boxMessages);
		this.boxService.saveForSystemBox(box);
	}

}
