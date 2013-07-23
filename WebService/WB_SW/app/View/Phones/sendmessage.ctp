<div class="actions">
	<h3><?php __('Actions'); ?></h3>
	<ul>
<li><?php echo $this->Html->link(__('Contact', true), array('controller' => 'users', 'action' => 'affiche2/'.$id)); ?> </li>
<li><?php echo $this->Html->link(__('Message', true), array('controller' => 'users', 'action' => 'affiche2')); ?> </li>
<li><?php echo $this->Html->link(__('Email', true), array('controller' => 'users', 'action' => 'affiche2')); ?> </li>
<li><?php echo $this->Html->link(__('Event', true), array('controller' => 'users', 'action' => 'affiche2')); ?> </li>
<li><?php echo $this->Html->link(__('Phone', true), array('controller' => 'users', 'action' => 'affiche2/'.'affichephone/'.$id)); ?> </li>
	</ul>
</div>
<div class="phones form">
<?php echo $this->Form->create('Phone');?>
	<fieldset>
 		<legend><?php __('Envoyer Notification a Votre Smartphone'); ?></legend>
	<?php
		echo $this->Form->input('message');
	?>
	</fieldset>
<?php echo $this->Form->end(__('Submit', true));?>
</div>	