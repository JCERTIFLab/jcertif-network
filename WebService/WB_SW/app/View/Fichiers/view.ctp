<div class="fichiers view">
<h2><?php  echo __('Fichier'); ?></h2>
	<dl>
		<dt><?php echo __('Id'); ?></dt>
		<dd>
			<?php echo h($fichier['Fichier']['id']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Name'); ?></dt>
		<dd>
			<?php echo h($fichier['Fichier']['name']); ?>
			&nbsp;
		</dd>
	</dl>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('Edit Fichier'), array('action' => 'edit', $fichier['Fichier']['id'])); ?> </li>
		<li><?php echo $this->Form->postLink(__('Delete Fichier'), array('action' => 'delete', $fichier['Fichier']['id']), null, __('Are you sure you want to delete # %s?', $fichier['Fichier']['id'])); ?> </li>
		<li><?php echo $this->Html->link(__('List Fichiers'), array('action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Fichier'), array('action' => 'add')); ?> </li>
	</ul>
</div>
