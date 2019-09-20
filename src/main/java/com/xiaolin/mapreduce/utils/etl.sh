#! /bin/bash


if [ $# -eq 1 ]; then
    time=$1
else
    time=`date -d "yesterday" +%Y%m%d`
fi


hadoop fs -rmr /hadoop/project/access_wide/day=$time
echo "step1: 删除正式目录中已经存在的分区的数据:"$time

hadoop fs -mkdir -p /hadoop/project/access_wide/day=$time
hadoop fs -mv /hadoop/project/wide/access/day=$time/part* /hadoop/project/access_wide/day=$time
echo "step2: 移动ETL后的数据到正式目录中:"$time

hadoop fs -rmr /hadoop/project/wide/access/day=$time
echo "step3: 删除ETL后产生的数据:"$time

hive -e "ALTER TABLE ruozedata_ba.access_wide ADD IF NOT EXISTS PARTITION(day="$time");"
echo "step4: 刷新元数据:"$time
