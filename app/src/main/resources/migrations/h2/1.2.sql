-- apply changes
create table npc_template_traits (
  id                            uuid not null,
  template_id                   uuid,
  name                          varchar(255),
  constraint uq_npc_template_traits_template_id_name unique (template_id,name),
  constraint pk_npc_template_traits primary key (id)
);

alter table npc_spawns drop constraint if exists fk_npc_spawns_template_id;
alter table npc_spawns add constraint fk_npc_spawns_template_id foreign key (template_id) references npc_templates (id) on delete cascade on update restrict;
create index ix_npc_template_traits_template_id on npc_template_traits (template_id);
alter table npc_template_traits add constraint fk_npc_template_traits_template_id foreign key (template_id) references npc_templates (id) on delete cascade on update restrict;

