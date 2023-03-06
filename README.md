## Getting Started

Setup your own MySQL Community Server. Create your own database and table. Names can be chosen freely.
For the table, the following columns MUST be named as followed (as for now):

char_name VARCHAR(16),
difficulty VARCHAR(10),
finished_in INT,
gear_drop VARCHAR(10),
gsc_of_drop INT,
run_date DATE,
run_id INT PRIMARY KEY AUTO_INCREMENT,
usefull_drop VARCHAR(10)

Inside App.java insert your user/database information.
App should then be running (variable URL in Database is for the standard MySQL Community-Edition installation).

## Planned
Implement some sort of setup so the desired database and table gets automatically created on users pc (if possible?).
Implement as Add-On into World of Warcraft, so everything gets automatically updated after a finished run.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
