create table user_data
(
	id int auto_increment,
	player varchar(16) null,
	uuid varchar(36) null,
	material varchar(24) null,
	custom_model_data int null,
	created_item text null,
	constraint user_data_pk
		primary key (id)
);

create index user_data_uuid_index
	on user_data (uuid);
