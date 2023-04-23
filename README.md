# 斗寿棋 游戏规则

## 游戏简介
Jungle Chess，又称斗寿棋，是一种现代中国棋类游戏。游戏在一个7×9的棋盘上进行，非常受到远东地区儿童的欢迎。Jungle Chess是一款两人策略游戏，被《花花公子获胜者指南》评为类似于西方的Stratego游戏。

## 游戏目标
玩家需要通过移动自己的动物棋子来占领对手的兽穴或者吃掉对手所有的动物棋子。

## 棋子和等级
每个玩家拥有8个不同等级的动物棋子，高等级的动物可以吃掉低等级或相同等级的动物。但是有一个特殊情况：象不能吃老鼠，而老鼠可以吃象。

| 等级 | 棋子 | 特殊移动 |
| --- | --- | --- |
| 8 | 象 | / |
| 7 | 狮 | 可以跳过河流 |
| 6 | 虎 | 也可以跳过河流 |
| 5 | 豹 | / |
| 4 | 狼 | / |
| 3 | 狗 | / |
| 2 | 猫 | / |
| 1 | 鼠 | 可以进入河流 |

## 棋盘和特殊地形
棋盘上有三种特殊地形：

1. 兽穴：自己的棋子不能进入自己的兽穴。如果玩家的棋子进入对手的兽穴，则玩家获胜。
2. 陷阱：如果一枚棋子进入对手的陷阱，则该棋子等级暂时变为0，直到离开陷阱。
3. 河流：河流位于棋盘中央，每个河流由一个2×3的矩形组成，共有三个河流。只有老鼠可以进入河流，而狮和虎可以跳过河流。

## 游戏初始化
游戏开始时，所有16个棋子都放置在棋盘上。初始状态如下图所示：

![Initial State of Jungle](https://pic.ntimg.cn/file/20200410/23136264_173349327424_2.jpg)

## 棋子移动
每回合玩家可以移动一枚自己的棋子到相邻的空格或者吃掉对手的一枚棋子。但是需要注意以下规则：

1. 棋子不能移动到自己的兽穴。
2. 被困在陷阱中的棋子等级变为0，只能在离开陷阱后恢复。
3. 只有老鼠可以进入河流，其他动物不能通过或停留在河流上。
4. 狮和虎可以跨过河流。
5. 象不能通过河流。

## 获胜条件
玩家可以通过以下两种方式获胜：

1. 占领对手的兽穴。
2. 吃掉对手所有的棋子。

## 结束游戏
游戏结束后，玩家可以选择重新开始或者退出游戏。

现在就来挑战Jungle Chess吧！
