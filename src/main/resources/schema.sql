DROP TABLE IF exists title ;

CREATE TABLE public.title (
	titleid varchar NOT NULL,
	"ordering" numeric NOT NULL,
	title varchar NULL,
	region varchar NULL,
	"language" varchar NULL,
	"types" varchar NULL,
	"attributes" varchar NULL,
	isoriginaltitle varchar NULL,
	CONSTRAINT tittle_order_id UNIQUE (titleid, ordering)
);