type Meta = {
  id: string,
  title: string,
  date: string,
  tags: string[]
}

type Course = {
  courseCode: string,
  courseName: string,
  courseInformation: string,
  academicUnits: number,
  broadeningAndDeepening: boolean,
  faculty: string,
  gradeType: string,
  score: number,
  total: number,
  prerequisites: string[][]
}

type ApiResponseCourses = {
  timestamp: Date,
  httpCode: number,
  status: string,
  message: string,
  data: Course[]
}

type ApiResponseCourse = {
  timestamp: Date,
  httpCode: number,
  status: string,
  message: string,
  data: Course
}