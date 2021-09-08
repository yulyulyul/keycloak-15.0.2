#!/bin/sh

#if[ ! -d "$KEYCLOAK_PATH" ]; then
# #	echo "unzip keycloak distribution version"
#rm -rf keycloak-15.0.2
#tar xfz distribution/server-dist/target/keycloak-15.0.2.tar.gz
#fi

 cd keycloak-15.0.2
 bin/standalone.sh -Djboss.as.management.blocking.timeout=3600
