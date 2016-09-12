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

  PRIMARY KEY pk_kb_knowledge(c_id),
  KEY k_kb_knowledge_classify(c_classify)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
