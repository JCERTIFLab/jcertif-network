<?php
echo $form->create('Fichier',array('type' => 'file','url' => '/fichiers/upload'));
    echo $form->input('filename', array('type' => 'file'))."".$this->Form->submit(__('Ajouter',true)).'</div>';

echo $form->end();
//echo '<br/><hr/>';

?>
