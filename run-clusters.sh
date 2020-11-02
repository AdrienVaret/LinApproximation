#! /bin/bash

min=$1
max=$2

CMD=""

for ((i=${min} ; i <= ${max} ; i++));
do
	id=9+$i
	CMD=CMD+"varet@192.168.200.${id} "
done

echo "${CMD}"