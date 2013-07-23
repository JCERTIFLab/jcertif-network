<div class="actions">
	<h3><?php __('Actions'); ?></h3>
	<ul>
<li><?php echo $this->Html->link(__('Contact', true), array('controller' => 'users', 'action' => 'affiche2/'.$id)); ?> </li>
<li><?php echo $this->Html->link(__('Message', true), array('controller' => 'users', 'action' => 'affiche2')); ?> </li>
<li><?php echo $this->Html->link(__('Email', true), array('controller' => 'users', 'action' => 'affiche2')); ?> </li>
<li><?php echo $this->Html->link(__('Event', true), array('controller' => 'users', 'action' => 'affiche2')); ?> </li>
<li><?php echo $this->Html->link(__('Phone', true), array('controller' => 'users', 'action' => 'affiche2/'.$id)); ?> </li>
		
	</ul>
</div>
<div class="users view">
<table cellpadding="0" cellspacing="0">
	<tr>
			<th><?php echo 'id';?></th>
			<th><?php echo 'model';?></th>
			<th><?php echo 'type';?></th>
			<th><?php echo 'os';?></th>
			<th><?php echo 'lat';?></th>
			<th><?php echo 'lng';?></th>
			<th class="actions"><?php __('Actions');?></th>
	</tr>
	<?php
	$i = 0;
	foreach ($phones as $phone):
		$class = null;
		if ($i++ % 2 == 0) {
			$class = ' class="altrow"';
		}
	?>
	<tr<?php echo $class;?>>
		<td><?php echo $phone['Phone']['id']; ?>&nbsp;</td>
		<td><?php echo $phone['Phone']['model']; ?>&nbsp;</td>
		<td><?php echo $phone['Phone']['type']; ?>&nbsp;</td>
		<td><?php echo $phone['Phone']['os']; ?>&nbsp;</td>
		<td><?php echo $phone['Phone']['lat']; ?>&nbsp;</td>
		<td><?php echo $phone['Phone']['lng']; ?>&nbsp;</td>

		<td class="actions">
		    <?php echo $this->Html->link(__('Localiser', true), array('action' => 'localisersmartphone', $phone['Phone']['id'])); ?>
			<?php echo $this->Html->link(__('Notification', true), array('action' => 'sendmessage', $phone['Phone']['id'])); ?>
			<?php echo $this->Html->link(__('View', true), array('action' => 'view', $phone['Phone']['id'])); ?>
			<?php echo $this->Html->link(__('Edit', true), array('action' => 'edit', $phone['Phone']['id'])); ?>
			<?php echo $this->Html->link(__('Delete', true), array('action' => 'delete', $phone['Phone']['id']), null, sprintf(__('Are you sure you want to delete # %s?', true), $phone['Phone']['id'])); ?>
		</td>
	</tr>
<?php endforeach; ?>
	</table>
	</div>