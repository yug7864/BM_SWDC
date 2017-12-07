<?php

include_once 'connection.php';
header('Content-Type: application/json');

class SelectState
{
    private $db;
    private $connection;

    function __construct()
    {
        $this -> db = new DB_Connection();
        $this -> connection = $this->db->getConnection();
    }

    public function getData($id,$port)
    {
      $query = "SELECT state
                FROM $id
                WHERE port =$port";

      $result = mysqli_query($this->connection,$query);

      $temp_array = array();

      if(mysqli_num_rows($result)>0)
      {

        $row = mysqli_fetch_assoc($result);
        echo json_encode($row);

        mysqli_close($this -> connection);
      }
      else
      {
        $json['error'] = ' ID 혹은 비밀번호를 확인 해주세요.';
        echo json_encode($json);
        mysqli_close($this -> connection);
      }

    }
  }

  $selectState = new SelectState();

  $id = $_POST["ID"];
  $port = $_POST["PORT"];

  if(empty($id) && empty($port))
  {
      echo json_encode("제발좀....");
  }
  else {
    $selectState -> getData($id,$port);

  }
 ?>
