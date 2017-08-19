<?php    
    if(isset($_POST['custom_msg']) && isset($_POST['server_key']) && isset($_POST['targets'])){

        $custom_msg = $_POST['custom_msg'];
        
        $server_key = $_POST['server_key'];
        $targets = $_POST['targets'];

        $fields = array(
            //'registration_ids' => $encoded_targets, //in this case $target is an array of tokens
            'registration_ids' => json_decode($targets),
            //'to' => $targets, //single target
            'priority' => "high",
            'data' => array ("custom_msg" => $custom_msg)
        );

        /*
        header('Content-type: application/json');
        $return_json = array('code'=> 401 , 'message' => 'suck with php', 'fields' => $fields);
        echo json_encode($return_json)
        */
        
        //FCM api URL
        $url = 'https://fcm.googleapis.com/fcm/send';
        
        //header with content_type api key
        $headers = array(
            'Content-Type:application/json',
            'Authorization:key='.$server_key
        );
            
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        $result = curl_exec($ch);
        if ($result === FALSE) {
            die('FCM Send Error: ' . curl_error($ch));
        }
        curl_close($ch);

        header('Content-type: application/json');

        $return_json = array('code'=> 200 , 'message' => 'success', 'server_response' => json_decode($result));
        echo json_encode($return_json);
    }
    else
    {
        $return_json = array('code'=> 401 , 'message' => 'Missing Required Parameters!');
        header('Content-type: application/json');
        echo json_encode($return_json);
    }
    
?>