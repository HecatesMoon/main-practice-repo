<?php

$tasks = [
    ["name" => "workout", "priority" => "high"],
    ["name" => "clean room", "priority" => "medium"],
    ["name" => "buy milk", "priority" => "low"],
    ["name" => "read chaper", "priority" => "low"],
    ["name" => "check pain at hospital", "priority" => "high"],
];

function printTask($task){
    echo "- " . $task["name"] . ", priority: " . $task["priority"] . "\n";
}

foreach($tasks as $task){
    printTask($task);
}

function filterByPriorty($tasks, $priority){
    $newArray = [];
    $index = 0;
    foreach($tasks as $task){
        if ($priority === $task["priority"]){
            $newArray[$index] = $task;
            $index++;
        }
    }

    return $newArray;
}

$highPriorityTasks = filterByPriorty($tasks,"high");

foreach($highPriorityTasks as $task){
    printTask($task);
}

?>