<div class="phones form">
<?php echo $this->Form->create('Phone'); ?>
	<fieldset>
		<legend><?php echo __('Add Phone'); ?></legend>
	<?php
		echo $this->Form->input('model');
		echo $this->Form->input('type');
		echo $this->Form->input('os');
		echo $this->Form->input('sender');
		echo $this->Form->input('user_id');
		echo $this->Form->input('lat');
		echo $this->Form->input('lng');
	?>
	</fieldset>
<?php echo $this->Form->end(__('Submit')); ?>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>

		<li><?php echo $this->Html->link(__('List Phones'), array('action' => 'index')); ?></li>
		<li><?php echo $this->Html->link(__('List Users'), array('controller' => 'users', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New User'), array('controller' => 'users', 'action' => 'add')); ?> </li>
	</ul>
</div>
