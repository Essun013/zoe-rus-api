DROP TABLE IF EXISTS t_common_classify;
CREATE TABLE t_common_classify
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_key VARCHAR(255) NOT NULL COMMENT '引用Key',
  c_parent CHAR(32) DEFAULT NULL COMMENT '上级分类ID',
  c_sort INT DEFAULT 0 COMMENT '显示顺序',
  c_name VARCHAR(255) DEFAULT NULL COMMENT '分类名称',
  c_label TEXT DEFAULT NULL COMMENT '标签',

  PRIMARY KEY pk_common_classify(c_id),
  KEY k_common_classify_key(c_key),
  KEY k_common_classify_parent(c_parent)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_common_classify values('42051871815346bb9f4da5e1479b3519','region',NULL,0,'中国',NULL);
INSERT INTO t_common_classify values('a040468120a743eba039fee5a653d087','region','42051871815346bb9f4da5e1479b3519',59,'福建省',NULL);
INSERT INTO t_common_classify values('b13e90d07bab429094c80d8e7e366639','region','a040468120a743eba039fee5a653d087',2,'厦门市',NULL);
INSERT INTO t_common_classify values('5cdbb3575ee34fac84f0856168ce4130','region','b13e90d07bab429094c80d8e7e366639',1,'思明区',NULL);

DROP TABLE IF EXISTS t_kb_key_word;
CREATE TABLE t_kb_key_word
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_word VARCHAR(255) NOT NULL COMMENT '关键词',
  c_knowledge CHAR(32) DEFAULT NULL COMMENT '知识ID',

  PRIMARY KEY pk_kb_key_word(c_id),
  KEY k_kb_key_word_word(c_word),
  KEY k_kb_key_word_knowledge(c_knowledge)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_kb_knowledge;
CREATE TABLE t_kb_knowledge
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_classify CHAR(32) NOT NULL COMMENT '分类',
  c_sort INT DEFAULT 0 COMMENT '顺序',
  c_subject VARCHAR(255) DEFAULT NULL COMMENT '标题',
  c_start INT DEFAULT 0 COMMENT '开始天数',
  c_end INT DEFAULT 0 COMMENT '结束天数',
  c_image VARCHAR(255) DEFAULT NULL COMMENT '主图',
  c_thumbnail VARCHAR(255) DEFAULT NULL COMMENT '缩略图',
  c_summary TEXT DEFAULT NULL COMMENT '摘要',
  c_label TEXT DEFAULT NULL COMMENT '标签',
  c_content TEXT DEFAULT NULL COMMENT '内容',
  c_html TEXT DEFAULT NULL COMMENT 'HTML内容',
  c_read INT DEFAULT 0 COMMENT '阅读数',
  c_favorite INT DEFAULT 0 COMMENT '收藏数',

  PRIMARY KEY pk_kb_knowledge(c_id),
  KEY k_kb_knowledge_classify(c_classify)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_kb_hospital;
CREATE TABLE t_kb_hospital
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_region CHAR(32) NOT NULL COMMENT '地区ID',
  c_name VARCHAR(255) NOT NULL COMMENT '名称',
  c_address VARCHAR(255) NOT NULL COMMENT '地址',
  c_longitude VARCHAR(255) DEFAULT NULL COMMENT '经度',
  c_latitude VARCHAR(255) DEFAULT NULL COMMENT '纬度',

  PRIMARY KEY pk_kb_hospital(c_id),
  KEY k_kb_hospital_region(c_region)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_milepost_physical;
CREATE TABLE t_milepost_physical
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_region CHAR(32) NOT NULL COMMENT '区域',
  c_hospital CHAR(32) DEFAULT NULL COMMENT '医院',
  c_type INT DEFAULT 0 COMMENT '类型：0-备孕期；1-孕期；2-婴幼儿期',
  c_sort INT DEFAULT 0 COMMENT '序号',
  c_time VARCHAR(255) DEFAULT NULL COMMENT '时间',
  c_content TEXT DEFAULT NULL COMMENT '内容',
  c_knowledge VARCHAR(255) DEFAULT NULL COMMENT '知识',

  PRIMARY KEY pk_milepost_physical(c_id),
  KEY k_milepost_physical_region(c_region)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_milepost_physical values('44a46f0d272b44938b402153365dfa56','42051871815346bb9f4da5e1479b3519',NULL,1,1,'0w-12w','{}','第1次产检 怀孕0到12周');
INSERT INTO t_milepost_physical values('285c5dcdaca14fb18fc8daaf6d19ee5c','42051871815346bb9f4da5e1479b3519',NULL,1,2,'16w-18w','{}','第2次产检 怀孕16到18周');
INSERT INTO t_milepost_physical values('a8fe656b2be14da89a7266c71f221f21','42051871815346bb9f4da5e1479b3519',NULL,1,3,'20w-24w','{}','第3次产检 怀孕20到24周');
INSERT INTO t_milepost_physical values('1663173e60694e139b928446fd1c009d','42051871815346bb9f4da5e1479b3519',NULL,1,4,'24w-28w','{}','第4次产检 怀孕24到28周');
INSERT INTO t_milepost_physical values('f37ed174181945ad9f3cf525a80cf530','42051871815346bb9f4da5e1479b3519',NULL,1,5,'28w-30w','{}','第5次产检 怀孕28到30周');
INSERT INTO t_milepost_physical values('8d6736584a1c440285a182fdc59c775b','42051871815346bb9f4da5e1479b3519',NULL,1,6,'30w-32w','{}','第6次产检 怀孕30到32周');
INSERT INTO t_milepost_physical values('8fe7f8fd533c40e29e33c4d30f026c36','42051871815346bb9f4da5e1479b3519',NULL,1,7,'32w-34w','{}','第7次产检 怀孕32到34周');
INSERT INTO t_milepost_physical values('b95bb72ed78b421b8a9d41ae19331e68','42051871815346bb9f4da5e1479b3519',NULL,1,8,'34w-36w','{}','第8次产检 怀孕34到36周');
INSERT INTO t_milepost_physical values('065c90482f734f6b91d05e8d2ed1d69b','42051871815346bb9f4da5e1479b3519',NULL,1,9,'37w','{}','第9次产检 怀孕37周');
INSERT INTO t_milepost_physical values('6f201d4a1aaa429c998536949d6e9705','42051871815346bb9f4da5e1479b3519',NULL,1,10,'38w','{}','第10次产检 怀孕38周');
INSERT INTO t_milepost_physical values('0ad576e27e7c463d97b12be7c5ae28ea','42051871815346bb9f4da5e1479b3519',NULL,1,11,'39w','{}','第11次产检 怀孕39周');
INSERT INTO t_milepost_physical values('14c648263fd442808b16637d650b31eb','42051871815346bb9f4da5e1479b3519',NULL,1,12,'40w','{}','第12次产检 怀孕40周');

DROP TABLE IF EXISTS t_uc_home;
CREATE TABLE t_uc_home
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_code CHAR(8) NOT NULL COMMENT '编码',
  c_name VARCHAR(255) DEFAULT NULL COMMENT '名称',
  c_register DATETIME DEFAULT NULL COMMENT '创建时间',

  PRIMARY KEY pk_uc_home(c_id),
  UNIQUE KEY uk_uc_home_code(c_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_uc_user;
CREATE TABLE t_uc_user
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_home CHAR(32) NOT NULL COMMENT '家庭',
  c_password CHAR(32) DEFAULT NULL COMMENT '密码',
  c_name VARCHAR(255) DEFAULT NULL COMMENT '姓名',
  c_nick VARCHAR(255) DEFAULT NULL COMMENT '昵称',
  c_mobile CHAR(11) DEFAULT NULL COMMENT '手机号',
  c_portrait VARCHAR(255) DEFAULT NULL COMMENT '头像',
  c_gender INT DEFAULT 0 COMMENT '性别：0-未知；1-男；2-女',
  c_address VARCHAR(255) DEFAULT NULL COMMENT '地址',
  c_birthday DATE DEFAULT NULL COMMENT '出生日期',
  c_register DATETIME DEFAULT NULL COMMENT '注册时间',

  PRIMARY KEY pk_uc_user(c_id),
  KEY k_uc_user_home(c_home)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_uc_auth;
CREATE TABLE t_uc_auth
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_user CHAR(32) DEFAULT NULL COMMENT '用户ID',
  c_username VARCHAR(255) NOT NULL COMMENT '用户名',
  c_type INT DEFAULT 0 COMMENT '类型：0-机器码；1-自有账号；其他为第三方',

  PRIMARY KEY pk_uc_auth(c_id),
  UNIQUE KEY uk_uc_auth_username(c_username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_uc_timeline;
CREATE TABLE t_uc_timeline
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_home CHAR(32) NOT NULL COMMENT '家庭',
  c_sort INT DEFAULT 0 COMMENT '顺序',
  c_type INT DEFAULT 0 COMMENT '类型：0-孕妈；1-宝宝',
  c_name VARCHAR(255) DEFAULT NULL COMMENT '名称',
  c_portrait VARCHAR(255) DEFAULT NULL COMMENT '头像',
  c_region CHAR(32) DEFAULT NULL COMMENT '地区',
  c_hospital CHAR(32) DEFAULT NULL COMMENT '医院',
  c_start DATE NOT NULL COMMENT '开始日期',
  c_day INT DEFAULT 0 COMMENT '天数',
  c_end INT DEFAULT 0 COMMENT '结束：0-进行中；1-已结束',

  PRIMARY KEY pk_uc_timeline(c_id),
  KEY k_uc_timeline_home(c_home)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_uc_favorite;
CREATE TABLE t_uc_favorite
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_user CHAR(32) NOT NULL COMMENT '用户',
  c_type INT DEFAULT 0 COMMENT '类型',
  c_goal CHAR(32) NOT NULL COMMENT '目标',
  c_time DATETIME NOT NULL COMMENT '时间',

  PRIMARY KEY pk_uc_favorite(c_id),
  UNIQUE KEY uk_uc_favorite_user_goal(c_user,c_goal),
  KEY k_uc_favorite_user_type(c_user,c_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_uc_feedback;
CREATE TABLE t_uc_feedback
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_user CHAR(32) DEFAULT NULL COMMENT '用户',
  c_content TEXT DEFAULT NULL COMMENT '内容',
  c_time DATETIME DEFAULT NULL COMMENT '时间',

  PRIMARY KEY pk_uc_feedback(c_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
