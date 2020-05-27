# Система автоматизации работы деканата

## Диаграммы
### Диаграмма классов всего проекта
![Картинка](http://salieri.me/java/entire_Diagram.png)
[Оригинал](http://salieri.me/java/entire_Diagram.png)

### Диаграмма базы данных
![Картинка](http://salieri.me/java/DB_Diagram.png)
[Оригинал](http://salieri.me/java/DB_Diagram.png)

### Диаграмма базы данных в коде
![Картинка](http://salieri.me/java/entity_Diagram.png)
[Оригинал](http://salieri.me/java/entity_Diagram.png)

## Таблица API
Всё API тут: http://api.salieri.me

API *почти* всегда возвращает что-то около нижеприведенного.
```json
{
  "version": "v0.1",
  "code": 200,
  "response": {
    
  }
}
```
Исключения: 404 (это все равно не надо было делать) и то, что я не смог перехватить.
<br><br>

Endpoint | Вход | Выход | Возможные коды
----------|------|-------|----------------
auth | String username<br>String password | String token | 201 400 422
register | String username<br>String password | SecuredUser user | 201 400 401 422
user/password.change | String oldPassword<br>String newPassword | | 200 400 401 403 422
user/groups.all | | Group[] groups | 200 401
user/groups.getStudents | Long id<br>String name | Person[] students | 200 400 401 422
user/marks.all | | Mark[] marks | 200 401
user/marks.byStudent | Long id | Mark[] marks | 200 400 401 422
user/marks.bySubject | Long id<br>String name | Mark[] marks | 200 400 401 422
user/marks.byTeacher | Long id | Mark[] marks | 200 400 401 422
user/marks.byGroup | Long id<br>String name | Mark[] marks | 200 400 401 422
user/people.all | | Mark[] marks | 200 401
user/students.all | | Mark[] marks | 200 401
user/subjects.all | | Mark[] marks | 200 401
user/teachers.all | | Mark[] marks | 200 401
admin/groups.create | String name | Group group | 201 401 403 422
admin/groups.delete | Long id<br>String name | | 200 401 403 422
admin/people.delete | Long id | | 200 401 403 422
admin/students.create | String firstName<br>String lastName<br>String patherName<br>String group | User student | 201 401 403 422
admin/subjects.create | String name | Subject subject | 201 401 403 422
admin/subjects.delete | Long id<br>String name | | 200 401 403 422
admin/teachers.create | String firstName<br>String lastName<br>String patherName | User teacher | 201 401 403 422
admin/users.all | | User[] users | 200 401 403
admin/users.delete | Long id<br>String username | | 200 401 403 422
admin/marks.create | Long student<br>Long subject<br>Long teacher<br>Integer value | Mark mark | 201 401 403 422
admin/marks.change | Long id<br>Integer value | | 200 401 403 422
