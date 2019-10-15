/*
编写一个 SQL 查询，查找所有至少连续出现三次的数字。

+----+-----+
| Id | Num |
+----+-----+
| 1  |  1  |
| 2  |  1  |
| 3  |  1  |
| 4  |  2  |
| 5  |  1  |
| 6  |  2  |
| 7  |  2  |
+----+-----+
例如，给定上面的 Logs 表， 1 是唯一连续出现至少三次的数字。

+-----------------+
| ConsecutiveNums |
+-----------------+
| 1               |
+-----------------+

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/consecutive-numbers
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

drop table if exists Logs;
create table Logs
(
	Id int default 0 not null primary key,
	Num int null
);
INSERT INTO Logs (Id, Num) VALUES (1, 1);
INSERT INTO Logs (Id, Num) VALUES (2, 1);
INSERT INTO Logs (Id, Num) VALUES (3, 1);
INSERT INTO Logs (Id, Num) VALUES (4, 2);
INSERT INTO Logs (Id, Num) VALUES (5, 1);
INSERT INTO Logs (Id, Num) VALUES (6, 2);
INSERT INTO Logs (Id, Num) VALUES (7, 2);
INSERT INTO Logs (Id, Num) VALUES (8, 0);
INSERT INTO Logs (Id, Num) VALUES (9, 0);
INSERT INTO Logs (Id, Num) VALUES (10, 0);
INSERT INTO Logs (Id, Num) VALUES (11, 0);

select distinct Num as ConsecutiveNums from (
    select Num, @c := (IF(@pre != (@pre := Num), 1, @c + 1)) as c
    from Logs, (select @c := 0, @pre := '') vars
) temp where c = 3;
