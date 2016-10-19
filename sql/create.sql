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
INSERT INTO t_common_classify values('42051871815346bb9f4da5e1479b3519','region',NULL,0,'中国');
INSERT INTO t_common_classify values('a040468120a743eba039fee5a653d087','region','42051871815346bb9f4da5e1479b3519',59,'福建省');
INSERT INTO t_common_classify values('b13e90d07bab429094c80d8e7e366639','region','a040468120a743eba039fee5a653d087',2,'厦门市');
INSERT INTO t_common_classify values('5cdbb3575ee34fac84f0856168ce4130','region','b13e90d07bab429094c80d8e7e366639',1,'思明区');

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
INSERT INTO t_milepost_physical values('f388e5e20342470fb0824ca34eb0d5c6','42051871815346bb9f4da5e1479b3519',NULL,11,1,'1w','{}','孕妈早知道1周');
INSERT INTO t_milepost_physical values('7598a78848fd4c06b31c48e9d53f1f30','42051871815346bb9f4da5e1479b3519',NULL,11,2,'2w','{}','孕妈早知道2周');
INSERT INTO t_milepost_physical values('ffd7e5ebd6444aeda966b6797bf6cb1e','42051871815346bb9f4da5e1479b3519',NULL,11,3,'3w','{}','孕妈早知道3周');
INSERT INTO t_milepost_physical values('85320669fc574c3ebc9e6275dd015082','42051871815346bb9f4da5e1479b3519',NULL,11,4,'4w','{}','孕妈早知道4周');
INSERT INTO t_milepost_physical values('887e0cf46af541bf9e1da1f5512011d6','42051871815346bb9f4da5e1479b3519',NULL,11,5,'5w','{}','孕妈早知道5周');
INSERT INTO t_milepost_physical values('c2c2086d35df4671926f0b8795f0444f','42051871815346bb9f4da5e1479b3519',NULL,11,6,'6w','{}','孕妈早知道6周');
INSERT INTO t_milepost_physical values('820f15bb2cae4505b29e598f40ab8990','42051871815346bb9f4da5e1479b3519',NULL,11,7,'7w','{}','孕妈早知道7周');
INSERT INTO t_milepost_physical values('96ff4496555844a68fd7214c58cef9c5','42051871815346bb9f4da5e1479b3519',NULL,11,8,'8w','{}','孕妈早知道8周');
INSERT INTO t_milepost_physical values('ae9c98f94a4447e9b69d1b0c3033f83b','42051871815346bb9f4da5e1479b3519',NULL,11,9,'9w','{}','孕妈早知道9周');
INSERT INTO t_milepost_physical values('17ad981aea13426c81804d5554d8710d','42051871815346bb9f4da5e1479b3519',NULL,11,10,'10w','{}','孕妈早知道10周');
INSERT INTO t_milepost_physical values('b79d83d2a491414b9d869f5183a17579','42051871815346bb9f4da5e1479b3519',NULL,11,11,'11w','{}','孕妈早知道11周');
INSERT INTO t_milepost_physical values('6b79ea5b3e18490697fea9171f2726fb','42051871815346bb9f4da5e1479b3519',NULL,11,12,'12w','{}','孕妈早知道12周');
INSERT INTO t_milepost_physical values('e70a94c1018f4df3a555424dab408340','42051871815346bb9f4da5e1479b3519',NULL,11,13,'13w','{}','孕妈早知道13周');
INSERT INTO t_milepost_physical values('f6b912ba561a41409c7915b88178e2cc','42051871815346bb9f4da5e1479b3519',NULL,11,14,'14w','{}','孕妈早知道14周');
INSERT INTO t_milepost_physical values('24139df6aed345679917f9f23247a0aa','42051871815346bb9f4da5e1479b3519',NULL,11,15,'15w','{}','孕妈早知道15周');
INSERT INTO t_milepost_physical values('3df5d5e8003d42a4972dfc7f59b43fe0','42051871815346bb9f4da5e1479b3519',NULL,11,16,'16w','{}','孕妈早知道16周');
INSERT INTO t_milepost_physical values('7dec0afb53274bfca0186d110fa3698c','42051871815346bb9f4da5e1479b3519',NULL,11,17,'17w','{}','孕妈早知道17周');
INSERT INTO t_milepost_physical values('43f5794329a7420f8a43cc7df4cabfa1','42051871815346bb9f4da5e1479b3519',NULL,11,18,'18w','{}','孕妈早知道18周');
INSERT INTO t_milepost_physical values('8903a8aaa4bc45b0b9b6e340d3339ae7','42051871815346bb9f4da5e1479b3519',NULL,11,19,'19w','{}','孕妈早知道19周');
INSERT INTO t_milepost_physical values('5efab9c48a7d489cae6418fee463447a','42051871815346bb9f4da5e1479b3519',NULL,11,20,'20w','{}','孕妈早知道20周');
INSERT INTO t_milepost_physical values('65665e38dfad48fdb1f1d5c2710d22a9','42051871815346bb9f4da5e1479b3519',NULL,11,21,'21w','{}','孕妈早知道21周');
INSERT INTO t_milepost_physical values('67b577fa37a54f8eac3d1717cc2214cf','42051871815346bb9f4da5e1479b3519',NULL,11,22,'22w','{}','孕妈早知道22周');
INSERT INTO t_milepost_physical values('8385a9d2d38c4e58aa3cba7f53abd9da','42051871815346bb9f4da5e1479b3519',NULL,11,23,'23w','{}','孕妈早知道23周');
INSERT INTO t_milepost_physical values('4ebbf0f9faa4466099a78082c547eaa2','42051871815346bb9f4da5e1479b3519',NULL,11,24,'24w','{}','孕妈早知道24周');
INSERT INTO t_milepost_physical values('355fe53887f94f488964ecc58b314fdc','42051871815346bb9f4da5e1479b3519',NULL,11,25,'25w','{}','孕妈早知道25周');
INSERT INTO t_milepost_physical values('a38f443789154663ad3d8dd993065656','42051871815346bb9f4da5e1479b3519',NULL,11,26,'26w','{}','孕妈早知道26周');
INSERT INTO t_milepost_physical values('52fb107c3434485cb77072c9c83609a2','42051871815346bb9f4da5e1479b3519',NULL,11,27,'27w','{}','孕妈早知道27周');
INSERT INTO t_milepost_physical values('48442cf728f44853b03b72942b77a44b','42051871815346bb9f4da5e1479b3519',NULL,11,28,'28w','{}','孕妈早知道28周');
INSERT INTO t_milepost_physical values('c070faa0fd9c4278bac7d19e6e5a641f','42051871815346bb9f4da5e1479b3519',NULL,11,29,'29w','{}','孕妈早知道29周');
INSERT INTO t_milepost_physical values('8ae86856546246ae9bb21356fa1339b6','42051871815346bb9f4da5e1479b3519',NULL,11,30,'30w','{}','孕妈早知道30周');
INSERT INTO t_milepost_physical values('ae8f78fefdc9413b81bbcc7d8ed587d5','42051871815346bb9f4da5e1479b3519',NULL,11,31,'31w','{}','孕妈早知道31周');
INSERT INTO t_milepost_physical values('33c33db7eea04f19832f6adbd587762d','42051871815346bb9f4da5e1479b3519',NULL,11,32,'32w','{}','孕妈早知道32周');
INSERT INTO t_milepost_physical values('8ae64ee02e964e4bb449e9c6948c26c4','42051871815346bb9f4da5e1479b3519',NULL,11,33,'33w','{}','孕妈早知道33周');
INSERT INTO t_milepost_physical values('d41b3257cbbd47f7a95de6accc0919eb','42051871815346bb9f4da5e1479b3519',NULL,11,34,'34w','{}','孕妈早知道34周');
INSERT INTO t_milepost_physical values('a7aa7658000b473eaf5ee7eb0f0d71ee','42051871815346bb9f4da5e1479b3519',NULL,11,35,'35w','{}','孕妈早知道35周');
INSERT INTO t_milepost_physical values('78d10e76842b461fa28377e528ec38c1','42051871815346bb9f4da5e1479b3519',NULL,11,36,'36w','{}','孕妈早知道36周');
INSERT INTO t_milepost_physical values('597970d60e0c4b338549992a6eb97cc7','42051871815346bb9f4da5e1479b3519',NULL,11,37,'37w','{}','孕妈早知道37周');
INSERT INTO t_milepost_physical values('c6495188d31f432687df30c3445e7030','42051871815346bb9f4da5e1479b3519',NULL,11,38,'38w','{}','孕妈早知道38周');
INSERT INTO t_milepost_physical values('36cea8364d8f4127bd9b861a0ca00210','42051871815346bb9f4da5e1479b3519',NULL,11,39,'39w','{}','孕妈早知道39周');
INSERT INTO t_milepost_physical values('535eca5f3985440b8972f86f4c45a669','42051871815346bb9f4da5e1479b3519',NULL,11,40,'40w','{}','孕妈早知道40周');
INSERT INTO t_milepost_physical values('63ef50b796e64a5aa03a77a3d0745166','42051871815346bb9f4da5e1479b3519',NULL,11,41,'41w','{}','孕妈早知道41周');
INSERT INTO t_milepost_physical values('2e89fb7373a84679bad3a4542159f47d','42051871815346bb9f4da5e1479b3519',NULL,11,42,'42w','{}','孕妈早知道42周');
INSERT INTO t_milepost_physical values('865247bb322f450bb681f8970ddfffff','42051871815346bb9f4da5e1479b3519',NULL,11,43,'43w','{}','孕妈早知道43周');
INSERT INTO t_milepost_physical values('d315e861fe994005b2fb5de9f566cf13','42051871815346bb9f4da5e1479b3519',NULL,11,44,'44w','{}','孕妈早知道44周');
INSERT INTO t_milepost_physical values('69776ba45ec144bcb65ea8b44483a1bf','42051871815346bb9f4da5e1479b3519',NULL,11,45,'45w','{}','孕妈早知道45周');
INSERT INTO t_milepost_physical values('a6c68ddfdc834299bacf6e790ef41ad2','42051871815346bb9f4da5e1479b3519',NULL,12,1,'1w','{}','宝宝成长1周');
INSERT INTO t_milepost_physical values('f9dcd77a7bc7450488b43b3ce1b75cd0','42051871815346bb9f4da5e1479b3519',NULL,12,2,'2w','{}','宝宝成长2周');
INSERT INTO t_milepost_physical values('5e3ac2c97ba24e3a8664845b8e6374a4','42051871815346bb9f4da5e1479b3519',NULL,12,3,'3w','{}','宝宝成长3周');
INSERT INTO t_milepost_physical values('fcaa374731ca4060beae49b8339968ad','42051871815346bb9f4da5e1479b3519',NULL,12,4,'4w','{}','宝宝成长4周');
INSERT INTO t_milepost_physical values('a57eebd4042d42808c0bc8064d422a8e','42051871815346bb9f4da5e1479b3519',NULL,12,5,'5w','{}','宝宝成长5周');
INSERT INTO t_milepost_physical values('4df7062cd2ab4204a3a6432b0915f63c','42051871815346bb9f4da5e1479b3519',NULL,12,6,'6w','{}','宝宝成长6周');
INSERT INTO t_milepost_physical values('159526dbf3f849dbbf17b633fa3c8fcb','42051871815346bb9f4da5e1479b3519',NULL,12,7,'7w','{}','宝宝成长7周');
INSERT INTO t_milepost_physical values('76713bd16dc84e1fadde31dfcaa02a30','42051871815346bb9f4da5e1479b3519',NULL,12,8,'8w','{}','宝宝成长8周');
INSERT INTO t_milepost_physical values('d66556cad8e1437d9961035c2e3e9acb','42051871815346bb9f4da5e1479b3519',NULL,12,9,'9w','{}','宝宝成长9周');
INSERT INTO t_milepost_physical values('a5b16804b5d1413994370d6c7e8a2e87','42051871815346bb9f4da5e1479b3519',NULL,12,10,'10w','{}','宝宝成长10周');
INSERT INTO t_milepost_physical values('34a26c72e2c44f2091e83883ffce1550','42051871815346bb9f4da5e1479b3519',NULL,12,11,'11w','{}','宝宝成长11周');
INSERT INTO t_milepost_physical values('a0f65cfc53c14a7ea05f52e3d115e094','42051871815346bb9f4da5e1479b3519',NULL,12,12,'12w','{}','宝宝成长12周');
INSERT INTO t_milepost_physical values('6eeaf740d3bb4271a1f33cb83c6b8b5b','42051871815346bb9f4da5e1479b3519',NULL,12,13,'13w','{}','宝宝成长13周');
INSERT INTO t_milepost_physical values('328251a7bbf849d08c24b88e73313e21','42051871815346bb9f4da5e1479b3519',NULL,12,14,'14w','{}','宝宝成长14周');
INSERT INTO t_milepost_physical values('de70c10625844b5aa571044a2230e19b','42051871815346bb9f4da5e1479b3519',NULL,12,15,'15w','{}','宝宝成长15周');
INSERT INTO t_milepost_physical values('44fe0c8e1f4f4cfc9b8c563f872f4fd8','42051871815346bb9f4da5e1479b3519',NULL,12,16,'16w','{}','宝宝成长16周');
INSERT INTO t_milepost_physical values('4d3987dbadd64cbe9f3be1c8bb96c113','42051871815346bb9f4da5e1479b3519',NULL,12,17,'17w','{}','宝宝成长17周');
INSERT INTO t_milepost_physical values('a486eec3b2c14e61a1bd721033e10b52','42051871815346bb9f4da5e1479b3519',NULL,12,18,'18w','{}','宝宝成长18周');
INSERT INTO t_milepost_physical values('559c9c8372e24604864288e985f1a812','42051871815346bb9f4da5e1479b3519',NULL,12,19,'19w','{}','宝宝成长19周');
INSERT INTO t_milepost_physical values('e7850bd0922d44608153ae181c87fc9e','42051871815346bb9f4da5e1479b3519',NULL,12,20,'20w','{}','宝宝成长20周');
INSERT INTO t_milepost_physical values('f9984645449e45d292258753837b9a0f','42051871815346bb9f4da5e1479b3519',NULL,12,21,'21w','{}','宝宝成长21周');
INSERT INTO t_milepost_physical values('c7c96d26e1f84abe86539a0b9aa76137','42051871815346bb9f4da5e1479b3519',NULL,12,22,'22w','{}','宝宝成长22周');
INSERT INTO t_milepost_physical values('452b19abadf74a43a1e3f39f8270af1a','42051871815346bb9f4da5e1479b3519',NULL,12,23,'23w','{}','宝宝成长23周');
INSERT INTO t_milepost_physical values('9cb2685dd53e478f9956cba4b6d28e28','42051871815346bb9f4da5e1479b3519',NULL,12,24,'24w','{}','宝宝成长24周');
INSERT INTO t_milepost_physical values('b57f192446ce4321acad62907b4ec40a','42051871815346bb9f4da5e1479b3519',NULL,12,25,'25w','{}','宝宝成长25周');
INSERT INTO t_milepost_physical values('f20dab8afaf1408fb97366fb184049ac','42051871815346bb9f4da5e1479b3519',NULL,12,26,'26w','{}','宝宝成长26周');
INSERT INTO t_milepost_physical values('7ba14c638dca40b7891293ffd1751fb4','42051871815346bb9f4da5e1479b3519',NULL,12,27,'27w','{}','宝宝成长27周');
INSERT INTO t_milepost_physical values('8c39d5f53c56487c804e9af69940cd0c','42051871815346bb9f4da5e1479b3519',NULL,12,28,'28w','{}','宝宝成长28周');
INSERT INTO t_milepost_physical values('8cd47a68fd67491ab36a3db6ef5b5e86','42051871815346bb9f4da5e1479b3519',NULL,12,29,'29w','{}','宝宝成长29周');
INSERT INTO t_milepost_physical values('57795f0d6e534aebac242f2db1f2e032','42051871815346bb9f4da5e1479b3519',NULL,12,30,'30w','{}','宝宝成长30周');
INSERT INTO t_milepost_physical values('0686a3dffee74c1dae9f7c5009a628f4','42051871815346bb9f4da5e1479b3519',NULL,12,31,'31w','{}','宝宝成长31周');
INSERT INTO t_milepost_physical values('e3218ca4ab894da7886e258226224f10','42051871815346bb9f4da5e1479b3519',NULL,12,32,'32w','{}','宝宝成长32周');
INSERT INTO t_milepost_physical values('2e9f844ad1d0471ba090857739d585e5','42051871815346bb9f4da5e1479b3519',NULL,12,33,'33w','{}','宝宝成长33周');
INSERT INTO t_milepost_physical values('1b9a505b148f4256aca5d150600fc604','42051871815346bb9f4da5e1479b3519',NULL,12,34,'34w','{}','宝宝成长34周');
INSERT INTO t_milepost_physical values('4d373afbb5024aa0b329ccad1e4c7091','42051871815346bb9f4da5e1479b3519',NULL,12,35,'35w','{}','宝宝成长35周');
INSERT INTO t_milepost_physical values('cfa37bae7a6345d790e833f365e3b963','42051871815346bb9f4da5e1479b3519',NULL,12,36,'36w','{}','宝宝成长36周');
INSERT INTO t_milepost_physical values('b61a1d7be05947fca60f7360398cf16c','42051871815346bb9f4da5e1479b3519',NULL,12,37,'37w','{}','宝宝成长37周');
INSERT INTO t_milepost_physical values('f8a6cab90a4b4419823c7b78ba75ea0b','42051871815346bb9f4da5e1479b3519',NULL,12,38,'38w','{}','宝宝成长38周');
INSERT INTO t_milepost_physical values('f3b03d60eab24b5295d68c6d2f2e41c8','42051871815346bb9f4da5e1479b3519',NULL,12,39,'39w','{}','宝宝成长39周');
INSERT INTO t_milepost_physical values('59fbb5f38a954ce29fa153fb5b383359','42051871815346bb9f4da5e1479b3519',NULL,12,40,'40w','{}','宝宝成长40周');
INSERT INTO t_milepost_physical values('28b9c9dd016d4fa1b303507bdebdfc06','42051871815346bb9f4da5e1479b3519',NULL,12,41,'41w','{}','宝宝成长41周');
INSERT INTO t_milepost_physical values('abc69bbe0aed4ccf9bbca863db6c474a','42051871815346bb9f4da5e1479b3519',NULL,12,42,'42w','{}','宝宝成长42周');
INSERT INTO t_milepost_physical values('8f6cb1fce0984f86af23eb659f06de96','42051871815346bb9f4da5e1479b3519',NULL,12,43,'43w','{}','宝宝成长43周');
INSERT INTO t_milepost_physical values('e50f1919abdd43d1bbc82053513a0f7c','42051871815346bb9f4da5e1479b3519',NULL,12,44,'44w','{}','宝宝成长44周');
INSERT INTO t_milepost_physical values('26827dcf12b4434280d1c1aab2565a2b','42051871815346bb9f4da5e1479b3519',NULL,12,45,'45w','{}','宝宝成长45周');

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
  c_portrait VARCHAR(255) DEFAULT NULL COMMENT '头像',
  c_start DATE NOT NULL COMMENT '开始日期',
  c_day INT DEFAULT 0 COMMENT '天数',
  c_end INT DEFAULT 0 COMMENT '结束：0-进行中；1-已结束',

  PRIMARY KEY pk_uc_timeline(c_id),
  KEY k_uc_timeline_home(c_home)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
