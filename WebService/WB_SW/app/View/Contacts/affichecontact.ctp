<table cellpadding="0" cellspacing="0">
	<tr>
			<th><?php echo 'id';?></th>
			<th><?php echo 'nom';?></th>
			<th><?php echo 'prenom';?></th>
			<th><?php echo 'num_tel';?></th>
			<th><?php echo 'num_fax';?></th>
			<th><?php echo 'email';?></th>
			<th><?php echo 'adresse';?></th>
			<th><?php echo 'modified';?></th>
			<th><?php echo 'created';?></th>
			<th><?php echo 'user_id';?></th>
			<th class="actions"><?php __('Actions');?></th>
	</tr>
	<?php
	$i = 0;
	foreach ($contacts as $contact):
		$class = null;
		if ($i++ % 2 == 0) {
			$class = ' class="altrow"';
		}
	?>
	<tr<?php echo $class;?>>
		<td><?php echo $contact['Contact']['id']; ?>&nbsp;</td>
		<td><?php echo $contact['Contact']['nom']; ?>&nbsp;</td>
		<td><?php echo $contact['Contact']['prenom']; ?>&nbsp;</td>
		<td><?php echo $contact['Contact']['num_tel']; ?>&nbsp;</td>
		<td><?php echo $contact['Contact']['num_fax']; ?>&nbsp;</td>
		<td><?php echo $contact['Contact']['email']; ?>&nbsp;</td>
		<td><?php echo $contact['Contact']['adresse']; ?>&nbsp;</td>
		<td><?php echo $contact['Contact']['modified']; ?>&nbsp;</td>
		<td><?php echo $contact['Contact']['created']; ?>&nbsp;</td>
		<td>
			<?php echo $this->Html->link($contact['User']['id'], array('controller' => 'users', 'action' => 'view', $contact['User']['id'])); ?>
		</td>
		<td class="actions">
			<?php echo $this->Html->link(__('View', true), array('action' => 'view', $contact['Contact']['id'])); ?>
			<?php echo $this->Html->link(__('Edit', true), array('action' => 'edit', $contact['Contact']['id'])); ?>
			<?php echo $this->Html->link(__('Delete', true), array('action' => 'delete', $contact['Contact']['id']), null, sprintf(__('Are you sure you want to delete # %s?', true), $contact['Contact']['id'])); ?>
		</td>
	</tr>
<?php endforeach; ?>
	</table>