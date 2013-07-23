<table cellpadding="0" cellspacing="0">
	<tr>
			<th><?php echo 'id';?></th>
			<th><?php echo 'content';?></th>
			<th><?php echo 'num_sender';?></th>
			<th><?php echo 'date';?></th>
			<th><?php echo 'source';?></th>
			<th><?php echo 'modified';?></th>
			<th><?php echo 'created';?></th>
			<th><?php echo 'user_id';?></th>
			<th class="actions"><?php __('Actions');?></th>
	</tr>
	<?php
	$i = 0;
	foreach ($messages as $message):
		$class = null;
		if ($i++ % 2 == 0) {
			$class = ' class="altrow"';
		}
	?>
	<tr<?php echo $class;?>>
		<td><?php echo $message['Message']['id']; ?>&nbsp;</td>
		<td><?php echo $message['Message']['content']; ?>&nbsp;</td>
		<td><?php echo $message['Message']['num_sender']; ?>&nbsp;</td>
		<td><?php echo $message['Message']['date']; ?>&nbsp;</td>
		<td><?php echo $message['Message']['source']; ?>&nbsp;</td>
		<td><?php echo $message['Message']['modified']; ?>&nbsp;</td>
		<td><?php echo $message['Message']['created']; ?>&nbsp;</td>
		<td>
			<?php echo $this->Html->link($message['User']['id'], array('controller' => 'users', 'action' => 'view', $message['User']['id'])); ?>
		</td>
		<td class="actions">
			<?php echo $this->Html->link(__('View', true), array('action' => 'view', $message['Message']['id'])); ?>
			<?php echo $this->Html->link(__('Edit', true), array('action' => 'edit', $message['Message']['id'])); ?>
			<?php echo $this->Html->link(__('Delete', true), array('action' => 'delete', $message['Message']['id']), null, sprintf(__('Are you sure you want to delete # %s?', true), $message['Message']['id'])); ?>
		</td>
	</tr>
<?php endforeach; ?>
	</table>