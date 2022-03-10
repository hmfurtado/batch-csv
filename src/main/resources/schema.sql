DROP TABLE IF exists title_db ;

CREATE TABLE public.title_db (
	tconst varchar NOT NULL,
	titletype varchar NULL,
	primarytitle varchar NULL,
	originaltitle varchar NULL,
	isadult varchar NULL,
	startyear varchar NULL,
	endyear varchar NULL,
	runtimeminutes varchar NULL,
	genres varchar NULL
);