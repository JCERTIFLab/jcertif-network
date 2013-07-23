<?php
$id =0;
foreach ($users as $m){
$m =$m['User'];
 echo "<li>".$m['login']."</li>" ;
 echo "<li>".$m['nom']."</li>";
 echo "<li>".$m['prenom']."</li>";
 echo "<li>".$m['email']."</li>";
  echo "<li>".$m['created']."</li>";
  $id=$m['id'];
}

?>
<div class="actions">
	<h3><?php __('Actions'); ?></h3>
	<ul>
<li><?php echo $this->Html->link(__('Contact', true), array('controller' => 'contacts', 'action' => 'affichecontact/'.$id)); ?> </li>
<li><?php echo $this->Html->link(__('Message', true), array('controller' => 'messages', 'action' => 'affichemessage/'.$id)); ?> </li>
<li><?php echo $this->Html->link(__('Email', true), array('controller' => 'emails', 'action' => 'afficheemail/'.$id)); ?> </li>
<li><?php echo $this->Html->link(__('Event', true), array('controller' => 'events', 'action' => 'afficheevent/'.$id)); ?> </li>
<li><?php echo $this->Html->link(__('Phone', true), array('controller' => 'phones', 'action' => 'affichephone/'.$id)); ?> </li>
<li><?php echo $this->Html->link(__('Image', true), array('controller' => 'users', 'action' => 'afficherlisteimage/')); ?> </li>
		
	</ul>
</div>


