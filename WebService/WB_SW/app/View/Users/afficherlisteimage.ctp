<html>
<HEAD><meta http-equiv="Refresh" content="100"> 
<TITLE>...</TITLE>
<STYLE type="text/css"><!--
H1 { color : blue }
figure {
    display: inline-block;
    height: 220px;
    width: 150px;
    text-align: center;
    vertical-align: middle;
    margin: 2px;
    padding: 4px;
    border: 1px solid #cfcfcf;
}
 
figure img {
    width: 100%;
    margin: 0px; 
    padding: 0px;
}
figure figcation {
    font-style: italic;
}
--></STYLE>
</HEAD>
<body>
<div class="users view">
<?php
foreach ($content as $contents){
echo " <figure>
				<img src='ftp://syrine:syrine@192.168.1.3/" . $contents. "' style=\"width: 130px;\" />
				<figcaption>$contents</figcaption>
				</figure>";
		}
       ?>
</div>
<div class="actions">
	<h3><?php __('Actions'); ?></h3>
	<ul>
<li><?php echo $this->Html->link(__('Contact', true), array('controller' => 'users', 'action' => 'affiche2/')); ?> </li>
<li><?php echo $this->Html->link(__('Message', true), array('controller' => 'users', 'action' => 'affiche2')); ?> </li>
<li><?php echo $this->Html->link(__('Email', true), array('controller' => 'users', 'action' => 'affiche2')); ?> </li>
<li><?php echo $this->Html->link(__('Event', true), array('controller' => 'users', 'action' => 'affiche2')); ?> </li>
<li><?php echo $this->Html->link(__('Phone', true), array('controller' => 'users', 'action' => 'affiche2/')); ?> </li>
		
	</ul>
</div>
</body>
</html>