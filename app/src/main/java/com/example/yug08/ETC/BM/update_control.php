<?php

include_once 'connection.php';
header('Content-Type: application/json');

class update
{
    private $db;
    private $connection;

    function __construct()
    {
        $this -> db = new DB_Connection();
        $this -> connection = $this->db->getConnection();
    }

    public function updateDate($id,$port,$state)
    {
      $query = "UPDATE $id SET state=$state WHERE port =$port";

      $result = mysqli_query($this->connection,$query);      
    }
  }

  $update = new update();

  $id = $_POST["ID"];
  $port = $_POST["PORT"];
  $state = $_POST["STATE"];

  if(empty($id) && empty($port))
  {
      echo json_encode("제발좀....");
  }
  else {
    $update -> updateDate($id,$port,$state);

  }
 ?>
