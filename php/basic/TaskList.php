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

function filterByPriority($tasks, $priority){
    
    return array_filter($tasks, function($task) use ($priority) {
        return $task["priority"] == $priority;
    });
}

$highPriorityTasks = filterByPriority($tasks,"high");

foreach($highPriorityTasks as $task){
    printTask($task);
}

?>