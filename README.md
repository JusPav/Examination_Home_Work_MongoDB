# Examination_Home_Work_MongoDB

Query:

use exam_db;

db.students.find();
db.students.drop();

db.exams.find();
db.exams.drop();

db.students.updateOne({"student.name":"as"}, {$set:{examName: "Testas2"}});

db.exams.updateOne({$and:[{"exam.examId":2},{"examAnswers.questionNumb":3}]},{$set:{"examAnswers.$.correctAnswers":"D"}});

db.exams.aggregate([{$project: {examName: 1, size: {$size: "$examAnswers"}}}, {$sort:{size: -1}},{$limit: 1 }]);

query to find exam with highest number of questions

db.exams.aggregate([{$project: {examName: 1, size: {$size: "$examAnswers"}}}, {$sort:{size: 1}},{$limit: 1 }]);

query to find exam with lowest number of questions

db.students.aggregate([{$project:{name:1,maxStudentRate:{$max:["$PHP.studentRate","$Java.studentRate","$Python.studentRate"]}}},{$sort:{maxStudentRate:-1}},{$limit: 1}]);

query to find student with highest rating

db.students.aggregate([{$project:{name:1,maxStudentRate:{$max:["$PHP.studentRate","$Java.studentRate","$Python.studentRate"]}}},{$sort:{maxStudentRate:1}},{$limit: 1}]);

query to find student with lowest rating
