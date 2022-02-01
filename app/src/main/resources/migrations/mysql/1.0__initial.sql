-- apply changes
create table npc_spawns (
  id                            varchar(40) not null,
  name                          varchar(255),
  template_id                   varchar(40),
  position                      varchar(255),
  constraint uq_npc_spawns_name unique (name),
  constraint pk_npc_spawns primary key (id)
);

create table npc_templates (
  id                            varchar(40) not null,
  name                          varchar(255),
  type                          varchar(255),
  skin                          varchar(2048),
  constraint uq_npc_templates_name unique (name),
  constraint pk_npc_templates primary key (id)
);

create index ix_npc_spawns_template_id on npc_spawns (template_id);
alter table npc_spawns add constraint fk_npc_spawns_template_id foreign key (template_id) references npc_templates (id) on delete restrict on update restrict;

