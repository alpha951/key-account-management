#!/bin/bash
BACKUP_DIR="./data/logs-backup"
DATE=$(date +%Y-%m-%d)

# Create backup directory if it doesn't exist
mkdir -p $BACKUP_DIR

# Compress and copy logs
tar -czf "$BACKUP_DIR/kampg-logs-$DATE.tar.gz" ./data/logs/

# Optional: Remove backups older than 30 days
find $BACKUP_DIR -name "kampg-logs-*.tar.gz" -mtime +30 -delete