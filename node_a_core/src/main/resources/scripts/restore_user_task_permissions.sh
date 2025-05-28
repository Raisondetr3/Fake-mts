#!/usr/bin/env bash

ENGINE_URL="http://localhost:8080/engine-rest"
AUTH_CREDENTIALS="-u d:d"
TASK_KEY="Activity_12o9p9on"
PROCESS_KEY="Process_0htt1dq"

echo "Fetching task IDs for definition key $TASK_KEY..."
TASK_IDS=$(curl -s $AUTH_CREDENTIALS \
  "$ENGINE_URL/task?taskDefinitionKey=$TASK_KEY" \
  | jq -r '.[].id')

for id in $TASK_IDS; do
  echo "Processing Task instance $id..."
  AUTH_IDS=$(curl -s $AUTH_CREDENTIALS \
    "$ENGINE_URL/authorization?groupId=user&resourceType=7&resourceId=$id&type=2" \
    | jq -r '.[].id')

  for auth in $AUTH_IDS; do
    echo "  Deleting per-instance revoke $auth"
    curl -s $AUTH_CREDENTIALS -X DELETE "$ENGINE_URL/authorization/$auth"
  done
done

echo "Processing Process Definition $PROCESS_KEY..."
AUTH_PD=$(curl -s $AUTH_CREDENTIALS \
  "$ENGINE_URL/authorization?groupId=user&resourceType=6&resourceId=$PROCESS_KEY&type=2" \
  | jq -r '.[].id')
if [ -n "$AUTH_PD" ]; then
  echo "  Deleting process-level revoke $AUTH_PD"
  curl -s $AUTH_CREDENTIALS -X DELETE "$ENGINE_URL/authorization/$AUTH_PD"
fi

echo "Processing global Task-level revoke..."
AUTH_GLOBAL=$(curl -s $AUTH_CREDENTIALS \
  "$ENGINE_URL/authorization?groupId=user&resourceType=7&resourceId=*&type=2" \
  | jq -r '.[].id')
if [ -n "$AUTH_GLOBAL" ]; then
  echo "  Deleting global revoke $AUTH_GLOBAL"
  curl -s $AUTH_CREDENTIALS -X DELETE "$ENGINE_URL/authorization/$AUTH_GLOBAL"
fi

echo "Restoration complete: ordinary users can now see and work on User Task $TASK_KEY."
