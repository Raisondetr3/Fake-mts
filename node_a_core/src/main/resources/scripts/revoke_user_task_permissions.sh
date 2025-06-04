#!/usr/bin/env bash

TASK_IDS=$(curl -s -u d:d \
  "http://localhost:8080/engine-rest/task?taskDefinitionKey=Activity_12o9p9o" \
  | jq -r '.[].id')

for id in $TASK_IDS; do
  echo "Revoking rights for Task $id …"

  curl -u d:d \
    -H "Content-Type: application/json" \
    -X POST "http://localhost:8080/engine-rest/authorization/create" \
    -d @- <<EOF
{
  "type": 2,
  "permissions": ["READ","UPDATE","TASK_WORK"],
  "groupId": "user",
  "resourceType": 7,
  "resourceId": "$id"
}
EOF

  echo "  → done"
done