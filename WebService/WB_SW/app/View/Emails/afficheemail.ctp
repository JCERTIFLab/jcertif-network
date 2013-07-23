<table cellpadding="0" cellspacing="0">
	<tr>
			<th><?php echo 'id';?></th>
			<th><?php echo 'content';?></th>
			<th><?php echo 'sender';?></th>
			<th><?php echo 'object';?></th>
			<th><?php echo 'date';?></th>
			<th><?php echo 'source';?></th>
			<th><?php echo 'modified';?></th>
			<th><?php echo 'created';?></th>
			<th><?php echo 'user_id';?></th>
			<th class="actions"><?php __('Actions');?></th>
	</tr>
	<?php
	$i = 0;
	foreach ($emails as $email):
		$class = null;
		if ($i++ % 2 == 0) {
			$class = ' class="altrow"';
		}
	?>
	<tr<?php echo $class;?>>
		<td><?php echo $email['Email']['id']; ?>&nbsp;</td>
		<td><?php echo $email['Email']['content']; ?>&nbsp;</td>
		<td><?php echo $email['Email']['sender']; ?>&nbsp;</td>
		<td><?php echo $email['Email']['object']; ?>&nbsp;</td>
		<td><?php echo $email['Email']['date']; ?>&nbsp;</td>
		<td><?php echo $email['Email']['source']; ?>&nbsp;</td>
		<td><?php echo $email['Email']['modified']; ?>&nbsp;</td>
		<td><?php echo $email['Email']['created']; ?>&nbsp;</td>
		<td>
			<?php echo $this->Html->link($email['User']['id'], array('controller' => 'users', 'action' => 'view', $email['User']['id'])); ?>
		</td>
		<td class="actions">
			<?php echo $this->Html->link(__('View', true), array('action' => 'view', $email['Email']['id'])); ?>
			<?php echo $this->Html->link(__('Edit', true), array('action' => 'edit', $email['Email']['id'])); ?>
			<?php echo $this->Html->link(__('Delete', true), array('action' => 'delete', $email['Email']['id']), null, sprintf(__('Are you sure you want to delete # %s?', true), $email['Email']['id'])); ?>
		</td>
	</tr>
<?php endforeach; ?>
	</table>