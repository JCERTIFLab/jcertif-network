<div class="users">
<?php echo $this->Form->create('User');?>
	<fieldset class="uers">
 		<legend class="leng"><?php __('Admin Login User'); ?></legend>
	<?php
		echo $this->Form->input('login');
		echo $this->Form->input('password');
	?>
	</fieldset>
<?php echo $this->Form->end(__('Submit', true));?>	
</div>