/*
编写一个 SQL 查询来实现分数排名。如果两个分数相同，则两个分数排名（Rank）相同。请注意，平分后的下一个名次应该是下一个连续的整数值。换句话说，名次之间不应该有“间隔”。

+----+-------+
| Id | Score |
+----+-------+
| 1  | 3.50  |
| 2  | 3.65  |
| 3  | 4.00  |
| 4  | 3.85  |
| 5  | 4.00  |
| 6  | 3.65  |
+----+-------+
例如，根据上述给定的 Scores 表，你的查询应该返回（按分数从高到低排列）：

+-------+------+
| Score | Rank |
+-------+------+
| 4.00  | 1    |
| 4.00  | 1    |
| 3.85  | 2    |
| 3.65  | 3    |
| 3.65  | 3    |
| 3.50  | 4    |
+-------+------+

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/rank-scores
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

drop table if exists Scores;
create table Scores
(
	id int auto_increment primary key,
	score float null
) comment '新值排名mysql测试';
INSERT INTO Scores (id, score) VALUES (1, 1.1);
INSERT INTO Scores (id, score) VALUES (2, 2.2);
INSERT INTO Scores (id, score) VALUES (3, 1.1);
INSERT INTO Scores (id, score) VALUES (4, 3.3);
INSERT INTO Scores (id, score) VALUES (5, 4.4);
INSERT INTO Scores (id, score) VALUES (6, 2.2);
INSERT INTO Scores (id, score) VALUES (7, 1.1);

-- 方法一(推荐)
select tb_l.score, rk as `rank` from Scores tb_l left join (
    select s.score, (@i := @i + 1) rk
    from (select distinct score from Scores order by score desc) s, (select @i := 0) i
) tb_r on tb_l.score = tb_r.score
order by rk;

-- 方法二(推荐,效率比方法一更好)
select score, @a := @a + (@pre != (@pre := score)) as `rank`
from Scores,(select @a := 0, @pre := '') t
order by score desc;

-- 方法三
select score, (select count(distinct score) from Scores where score >= s.score) `rank` from Scores s order by score desc;

/*
+-------+------+
| score | rk   |
+-------+------+
|   4.4 |    1 |
|   3.3 |    2 |
|   2.2 |    3 |
|   2.2 |    3 |
|   1.1 |    4 |
|   1.1 |    4 |
|   1.1 |    4 |
+-------+------+
 */

-- 随机插入大量数据比较两种方案的优劣
drop function if exists insertRandData;
create function insertRandData()
returns int
begin
    declare i int;
    set i = 0;
    delete from Scores where 1 = 1;
    while i < 1000 do
        INSERT INTO Scores (id, score) VALUES (null, i % 100);
        set i = i + 1;
    end while;
    return 0;
end;
select insertRandData();
drop function insertRandData;
