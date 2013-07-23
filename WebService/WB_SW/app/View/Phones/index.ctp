<div class="phones index">
	<h2><?php echo __('Phones'); ?></h2>
	<table cellpadding="0" cellspacing="0">
	<tr>
			<th><?php echo $this->Paginator->sort('id'); ?></th>
			<th><?php echo $this->Paginator->sort('model'); ?></th>
			<th><?php echo $this->Paginator->sort('type'); ?></th>
			<th><?php echo $this->Paginator->sort('os'); ?></th>
			<th><?php echo $this->Paginator->sort('sender'); ?></th>
			<th><?php echo $this->Paginator->sort('user_id'); ?></th>
			<th><?php echo $this->Paginator->sort('created'); ?></th>
			<th><?php echo $this->Paginator->sort('lat'); ?></th>
			<th><?php echo $this->Paginator->sort('lng'); ?></th>
			<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	<?php foreach ($phones as $phone): ?>
	<tr>
		<td><?php echo h($phone['Phone']['id']); ?>&nbsp;</td>
		<td><?php echo h($phone['Phone']['model']); ?>&nbsp;</td>
		<td><?php echo h($phone['Phone']['type']); ?>&nbsp;</td>
		<td><?php echo h($phone['Phone']['os']); ?>&nbsp;</td>
		<td><?php echo h($phone['Phone']['sender']); ?>&nbsp;</td>
		<td>
			<?php echo $this->Html->link($phone['User']['id'], array('controller' => 'users', 'action' => 'view', $phone['User']['id'])); ?>
		</td>
		<td><?php echo h($phone['Phone']['created']); ?>&nbsp;</td>
		<td><?php echo h($phone['Phone']['lat']); ?>&nbsp;</td>
		<td><?php echo h($phone['Phone']['lng']); ?>&nbsp;</td>
		<td class="actions">
			<?php echo $this->Html->link(__('View'), array('action' => 'view', $phone['Phone']['id'])); ?>
			<?php echo $this->Html->link(__('Edit'), array('action' => 'edit', $phone['Phone']['id'])); ?>
			<?php echo $this->Form->postLink(__('Delete'), array('action' => 'delete', $phone['Phone']['id']), null, __('Are you sure you want to delete # %s?', $phone['Phone']['id'])); ?>
		</td>
	</tr>
<?php endforeach; ?>
	</table>
	<p>
	<?php
	echo $this->Paginator->counter(array(
	'format' => __('Page {:page} of {:pages}, showing {:current} records out of {:count} total, starting on record {:start}, ending on {:end}')
	));
	?>	</p>
	<div class="paging">
	<?php
		echo $this->Paginator->prev('< ' . __('previous'), array(), null, array('class' => 'prev disabled'));
		echo $this->Paginator->numbers(array('separator' => ''));
		echo $this->Paginator->next(__('next') . ' >', array(), null, array('class' => 'next disabled'));
	?>
	</div>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('New Phone'), array('action' => 'add')); ?></li>
		<li><?php echo $this->Html->link(__('List Users'), array('controller' => 'users', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New User'), array('controller' => 'users', 'action' => 'add')); ?> </li>
	</ul>
</div>
