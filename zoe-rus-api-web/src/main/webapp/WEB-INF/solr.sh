#!/bin/bash

cd $1
bin/solr delete -c rus
bin/solr create -c rus
bin/post -c rus -filetypes md $2
