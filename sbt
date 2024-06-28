bash
#!/bin/bash

# Use lowercase variable names
sbt_opts="-Xms512M -Xmx1536M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=256M" 

# Quote variable expansion to prevent word splitting
java "$sbt_opts" -jar "$(dirname "$0")/sbt-launch.jar" "$@"
