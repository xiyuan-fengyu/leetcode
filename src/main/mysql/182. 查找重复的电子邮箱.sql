/*
 编写一个 SQL 查询，查找 Person 表中所有重复的电子邮箱。

示例：

+----+---------+
| Id | Email   |
+----+---------+
| 1  | a@b.com |
| 2  | c@d.com |
| 3  | a@b.com |
+----+---------+
根据以上输入，你的查询应返回以下结果：

+---------+
| Email   |
+---------+
| a@b.com |
+---------+

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/duplicate-emails
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

drop table if exists Person;
create table Person (Id int primary key, Email varchar(255));
drop function if exists insertRandData;
create function insertRandData()
    returns int
begin
    declare i int;
    set i = 1;
    while i <= 10000 do
    INSERT INTO Person (Id, Email) VALUES (i, concat(cast(rand() * 100 as int), '@test.com'));
    set i = i + 1;
    end while;
    return 0;
end;
select insertRandData();
drop function insertRandData;

-- 关闭缓存，测试效果
SET SESSION query_cache_type=0;

-- 方案1，效率最好，平均 100ms
select Email from (
    select Email, count(*) c from Person group by Email
) temp where temp.c >= 2;

--  方案2，115ms
select Email from Person group by Email having count(Email) > 1;

-- 方案3，125ms
select Email from (
    select Email, (@c := if(@pre != (@pre := Email), 1, @c + 1)) c from Person,(select @c := 0, @pre := '') vars order by Email
) temp where c = 2;
