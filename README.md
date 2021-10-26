# myraft

本项目使用Java8实现Raft共识算法，构建工具使用Maven。

考虑到扩展性，本项目设计实现的Raft算法分为一下两部分。
1. Raft核心：raft-core;
2. 典型状态机KV服务：raft-kvstore.

按照如下顺序设计并实现：
1. 核心功能：选举。
2. 核心功能：日志复制。
3. 客户端。
4. 日志快照。
5. 集群成员管理。
6. 其他优化。

设计和实现的原则是从核心开始，一点一点添加功能，这样可以由简到难。
实现完一个组件后需要做相关的测试，以保证组件的正确性。

核心之后是客户端。虽然很多 Raft 算法都没有给出客户端的部分。
但是客户端对于一个完整的 Raft 算法实现是必需的部分。
而且有了客户端，就能更早地从使用方角度测试代码的正确性。
第4项日志快照是 Raft 算法给出的一个优化方案。
没有日志快照的状态机基本上不可能用于生产环境，所以这里将日志快照作为必要项列。

另一个生产环境下非常必要的功能是集群成员管理，即增加成员服务器、移除成员服务器等操作。
Diego Ongaro 的博士论文提出了一种叫作“单服务器变更”（ Single-Server Membership
Change ）的集群成员管理方案，并且给出了实现上的细节。
本项目实现的是单服务器变更。

最后是 Raft 算法的其他优化方案，这部分主要作为参考，本项目并没有完全实现这些优化。

