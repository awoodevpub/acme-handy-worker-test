
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxService {

	// Managed repository
	@Autowired
	private BoxRepository	boxRepository;

	// Supporting services
	@Autowired
	private ActorService	actorService;


	// Simple CRUD methods
	// R9.4
	public Box create() {
		Box result;

		final Actor sender = this.actorService.findActorLogged();
		Assert.notNull(sender);
		Assert.isTrue(!sender.getIsSuspicious());

		result = new Box();
		final Collection<Message> messages = new HashSet<>();

		result.setMessages(messages);
		result.setIsSystemBox(false);

		return result;
	}

	public Collection<Box> findAll() {
		Collection<Box> result;

		result = this.boxRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Box findOne(final int boxId) {
		Assert.isTrue(boxId != 0);

		Box result;

		result = this.boxRepository.findOne(boxId);
		Assert.notNull(result);

		return result;
	}

	// R9.4
	public Box save(final Box box) {
		Assert.notNull(box);

		Box result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());

		final Collection<Box> boxesActorLogged = actorLogged.getBoxes();

		if (box.getId() == 0) {
			result = this.boxRepository.save(box);
			boxesActorLogged.add(result);
			actorLogged.setBoxes(boxesActorLogged);
			this.actorService.save(actorLogged);
		} else {
			Assert.isTrue(boxesActorLogged.contains(box));
			Assert.isTrue(!box.getIsSystemBox());
			result = this.boxRepository.save(box);
		}

		return result;
	}

	// R9.4
	public void delete(final Box box) {
		Assert.notNull(box);
		Assert.isTrue(box.getId() != 0);
		Assert.isTrue(this.boxRepository.exists(box.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());

		if (box.getId() != 0)
			Assert.isTrue(actorLogged.getBoxes().contains(box));

		Assert.isTrue(!box.getIsSystemBox());

		final Collection<Box> boxesActorLogged = actorLogged.getBoxes();
		boxesActorLogged.remove(box);
		actorLogged.setBoxes(boxesActorLogged);
		this.actorService.save(actorLogged);

		this.boxRepository.delete(box);
	}

	// Other business methods
	public Box createForSystemBox() {
		Box result;

		result = new Box();
		final Collection<Message> messages = new HashSet<>();

		result.setMessages(messages);
		result.setIsSystemBox(true);

		return result;
	}

	public Box saveForSystemBox(final Box box) {
		Assert.notNull(box);

		Box result;

		result = this.boxRepository.save(box);

		return result;
	}

	public Box findOutBoxByActorId(final int actorId) {
		Assert.isTrue(actorId != 0);

		Box result;

		result = this.boxRepository.findOutBoxByActorId(actorId);
		return result;
	}

	public Box findSpamBoxByActorId(final int actorId) {
		Assert.isTrue(actorId != 0);

		Box result;

		result = this.boxRepository.findSpamBoxByActorId(actorId);
		return result;
	}

	public Box findInBoxByActorId(final int actorId) {
		Assert.isTrue(actorId != 0);

		Box result;

		result = this.boxRepository.findInBoxByActorId(actorId);
		return result;
	}

	public Box findTrashBoxByActorId(final int actorId) {
		Assert.isTrue(actorId != 0);

		Box result;

		result = this.boxRepository.findTrashBoxByActorId(actorId);
		return result;
	}

	public Collection<Box> findAllBoxByActorLogged() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		Assert.isTrue(actorLogged.getUserAccount().getStatusAccount());

		Collection<Box> result;

		result = this.boxRepository.findAllBoxByActorId(actorLogged.getId());
		return result;
	}

	public Collection<Box> findBoxesByMessageId(final int messageId) {
		Collection<Box> result;

		result = this.boxRepository.findBoxesByMessageId(messageId);
		return result;
	}

	public Box getBoxFromMessageIdActorLogged(final int messageId) {
		Box result;

		final Actor actorLogged = this.actorService.findActorLogged();

		result = this.boxRepository.getBoxFromMessageIdActorLogged(messageId, actorLogged.getId());
		return result;
	}

}
