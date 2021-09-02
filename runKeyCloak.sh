#!/bin/sh
rm -rf keycloak-15.0.2
tar xfz distribution/server-dist/target/keycloak-15.0.2.tar.gz
cd keycloak-15.0.2
bin/standalone.sh
