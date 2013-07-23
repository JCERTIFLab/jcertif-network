<div class="users view">
<h2><?php  echo __('User'); ?></h2>
	<dl>
		<dt><?php echo __('Id'); ?></dt>
		<dd>
			<?php echo h($user['User']['id']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Login'); ?></dt>
		<dd>
			<?php echo h($user['User']['login']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Password'); ?></dt>
		<dd>
			<?php echo h($user['User']['password']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Nom'); ?></dt>
		<dd>
			<?php echo h($user['User']['nom']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Prenom'); ?></dt>
		<dd>
			<?php echo h($user['User']['prenom']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Email'); ?></dt>
		<dd>
			<?php echo h($user['User']['email']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Created'); ?></dt>
		<dd>
			<?php echo h($user['User']['created']); ?>
			&nbsp;
		</dd>
	</dl>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('Edit User'), array('action' => 'edit', $user['User']['id'])); ?> </li>
		<li><?php echo $this->Form->postLink(__('Delete User'), array('action' => 'delete', $user['User']['id']), null, __('Are you sure you want to delete # %s?', $user['User']['id'])); ?> </li>
		<li><?php echo $this->Html->link(__('List Users'), array('action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New User'), array('action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Contacts'), array('controller' => 'contacts', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Contact'), array('controller' => 'contacts', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Emails'), array('controller' => 'emails', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Email'), array('controller' => 'emails', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Events'), array('controller' => 'events', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Event'), array('controller' => 'events', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Messages'), array('controller' => 'messages', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Message'), array('controller' => 'messages', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Phones'), array('controller' => 'phones', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Phone'), array('controller' => 'phones', 'action' => 'add')); ?> </li>
	</ul>
</div>
<div class="related">
	<h3><?php echo __('Related Contacts'); ?></h3>
	<?php if (!empty($user['Contact'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Nom'); ?></th>
		<th><?php echo __('Prenom'); ?></th>
		<th><?php echo __('Num Tel'); ?></th>
		<th><?php echo __('Num Fax'); ?></th>
		<th><?php echo __('Email'); ?></th>
		<th><?php echo __('Adresse'); ?></th>
		<th><?php echo __('User Id'); ?></th>
		<th><?php echo __('Created'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php
		$i = 0;
		foreach ($user['Contact'] as $contact): ?>
		<tr>
			<td><?php echo $contact['id']; ?></td>
			<td><?php echo $contact['nom']; ?></td>
			<td><?php echo $contact['prenom']; ?></td>
			<td><?php echo $contact['num_tel']; ?></td>
			<td><?php echo $contact['num_fax']; ?></td>
			<td><?php echo $contact['email']; ?></td>
			<td><?php echo $contact['adresse']; ?></td>
			<td><?php echo $contact['user_id']; ?></td>
			<td><?php echo $contact['created']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'contacts', 'action' => 'view', $contact['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'contacts', 'action' => 'edit', $contact['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'contacts', 'action' => 'delete', $contact['id']), null, __('Are you sure you want to delete # %s?', $contact['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Contact'), array('controller' => 'contacts', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
<div class="related">
	<h3><?php echo __('Related Emails'); ?></h3>
	<?php if (!empty($user['Email'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Content'); ?></th>
		<th><?php echo __('Sender'); ?></th>
		<th><?php echo __('Object'); ?></th>
		<th><?php echo __('Source'); ?></th>
		<th><?php echo __('User Id'); ?></th>
		<th><?php echo __('Created'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php
		$i = 0;
		foreach ($user['Email'] as $email): ?>
		<tr>
			<td><?php echo $email['id']; ?></td>
			<td><?php echo $email['content']; ?></td>
			<td><?php echo $email['sender']; ?></td>
			<td><?php echo $email['object']; ?></td>
			<td><?php echo $email['source']; ?></td>
			<td><?php echo $email['user_id']; ?></td>
			<td><?php echo $email['created']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'emails', 'action' => 'view', $email['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'emails', 'action' => 'edit', $email['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'emails', 'action' => 'delete', $email['id']), null, __('Are you sure you want to delete # %s?', $email['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Email'), array('controller' => 'emails', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
<div class="related">
	<h3><?php echo __('Related Events'); ?></h3>
	<?php if (!empty($user['Event'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Content'); ?></th>
		<th><?php echo __('Date'); ?></th>
		<th><?php echo __('Source'); ?></th>
		<th><?php echo __('Category Id'); ?></th>
		<th><?php echo __('User Id'); ?></th>
		<th><?php echo __('Created'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php
		$i = 0;
		foreach ($user['Event'] as $event): ?>
		<tr>
			<td><?php echo $event['id']; ?></td>
			<td><?php echo $event['content']; ?></td>
			<td><?php echo $event['date']; ?></td>
			<td><?php echo $event['source']; ?></td>
			<td><?php echo $event['category_id']; ?></td>
			<td><?php echo $event['user_id']; ?></td>
			<td><?php echo $event['created']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'events', 'action' => 'view', $event['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'events', 'action' => 'edit', $event['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'events', 'action' => 'delete', $event['id']), null, __('Are you sure you want to delete # %s?', $event['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Event'), array('controller' => 'events', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
<div class="related">
	<h3><?php echo __('Related Messages'); ?></h3>
	<?php if (!empty($user['Message'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Content'); ?></th>
		<th><?php echo __('Num Sender'); ?></th>
		<th><?php echo __('Date'); ?></th>
		<th><?php echo __('Source'); ?></th>
		<th><?php echo __('User Id'); ?></th>
		<th><?php echo __('Created'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php
		$i = 0;
		foreach ($user['Message'] as $message): ?>
		<tr>
			<td><?php echo $message['id']; ?></td>
			<td><?php echo $message['content']; ?></td>
			<td><?php echo $message['num_sender']; ?></td>
			<td><?php echo $message['date']; ?></td>
			<td><?php echo $message['source']; ?></td>
			<td><?php echo $message['user_id']; ?></td>
			<td><?php echo $message['created']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'messages', 'action' => 'view', $message['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'messages', 'action' => 'edit', $message['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'messages', 'action' => 'delete', $message['id']), null, __('Are you sure you want to delete # %s?', $message['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Message'), array('controller' => 'messages', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
<div class="related">
	<h3><?php echo __('Related Phones'); ?></h3>
	<?php if (!empty($user['Phone'])): ?>
	<table cellpadding = "0" cellspacing = "0">
	<tr>
		<th><?php echo __('Id'); ?></th>
		<th><?php echo __('Model'); ?></th>
		<th><?php echo __('Type'); ?></th>
		<th><?php echo __('Os'); ?></th>
		<th><?php echo __('Sender'); ?></th>
		<th><?php echo __('User Id'); ?></th>
		<th><?php echo __('Created'); ?></th>
		<th><?php echo __('Lat'); ?></th>
		<th><?php echo __('Lng'); ?></th>
		<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php
		$i = 0;
		foreach ($user['Phone'] as $phone): ?>
		<tr>
			<td><?php echo $phone['id']; ?></td>
			<td><?php echo $phone['model']; ?></td>
			<td><?php echo $phone['type']; ?></td>
			<td><?php echo $phone['os']; ?></td>
			<td><?php echo $phone['sender']; ?></td>
			<td><?php echo $phone['user_id']; ?></td>
			<td><?php echo $phone['created']; ?></td>
			<td><?php echo $phone['lat']; ?></td>
			<td><?php echo $phone['lng']; ?></td>
			<td class="actions">
				<?php echo $this->Html->link(__('View'), array('controller' => 'phones', 'action' => 'view', $phone['id'])); ?>
				<?php echo $this->Html->link(__('Edit'), array('controller' => 'phones', 'action' => 'edit', $phone['id'])); ?>
				<?php echo $this->Form->postLink(__('Delete'), array('controller' => 'phones', 'action' => 'delete', $phone['id']), null, __('Are you sure you want to delete # %s?', $phone['id'])); ?>
			</td>
		</tr>
	<?php endforeach; ?>
	</table>
<?php endif; ?>

	<div class="actions">
		<ul>
			<li><?php echo $this->Html->link(__('New Phone'), array('controller' => 'phones', 'action' => 'add')); ?> </li>
		</ul>
	</div>
</div>
