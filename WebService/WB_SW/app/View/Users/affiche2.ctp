<div class="actions">
	<h3><?php __('Actions'); ?></h3>
	<ul>
<li><?php echo $this->Html->link(__('Contact', true), array('controller' => 'users', 'action' => 'affiche2/'.$id)); ?> </li>
<li><?php echo $this->Html->link(__('Message', true), array('controller' => 'users', 'action' => 'affiche2')); ?> </li>
<li><?php echo $this->Html->link(__('Email', true), array('controller' => 'users', 'action' => 'affiche2')); ?> </li>
<li><?php echo $this->Html->link(__('Event', true), array('controller' => 'users', 'action' => 'affiche2')); ?> </li>
<li><?php echo $this->Html->link(__('Phone', true), array('controller' => 'phones', 'action' => 'affichephone/'.$id)); ?> </li>
		
	</ul>
</div>

