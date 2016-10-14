DROP TABLE IF EXISTS t_common_classify;
CREATE TABLE t_common_classify
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_key VARCHAR(255) NOT NULL COMMENT '引用Key',
  c_parent CHAR(32) DEFAULT NULL COMMENT '上级分类ID',
  c_sort INT DEFAULT 0 COMMENT '显示顺序',
  c_name VARCHAR(255) DEFAULT NULL COMMENT '分类名称',

  PRIMARY KEY pk_common_classify(c_id),
  KEY k_common_classify_key(c_key),
  KEY k_common_classify_parent(c_parent)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  c_classify CHAR(32) DEFAULT NULL COMMENT '分类ID',
  c_sort INT DEFAULT 0 COMMENT '显示顺序',
  c_subject VARCHAR(255) NOT NULL COMMENT '标题',
  c_content TEXT DEFAULT NULL COMMENT '内容',
  c_html TEXT DEFAULT NULL COMMENT 'HTML内容',

  PRIMARY KEY pk_kb_knowledge(c_id),
  KEY k_kb_knowledge_classify(c_classify)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_kb_hospital;
CREATE TABLE t_kb_hospital
(
  c_id CHAR(32) NOT NULL COMMENT '主键',
  c_region CHAR(32) DEFAULT NULL COMMENT '分类ID',
  c_name VARCHAR(255) NOT NULL COMMENT '标题',
  c_longitude VARCHAR(255) NOT NULL COMMENT '经度',
  c_latitude VARCHAR(255) NOT NULL COMMENT '纬度',

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
  c_start DATE NOT NULL COMMENT '开始日期',
  c_day INT DEFAULT 0 COMMENT '天数',
  c_end INT DEFAULT 0 COMMENT '结束：0-进行中；1-已结束',

  PRIMARY KEY pk_uc_timeline(c_id),
  KEY k_uc_timeline_home(c_home)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
