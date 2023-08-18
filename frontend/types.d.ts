type Meta = {
  id: string,
  title: string,
  date: string,
  tags: string[]
}

type Module = {
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

type ApiResponseModules = {
  timestamp: Date,
  httpCode: number,
  status: string,
  message: string,
  data: Module[]
}

type ApiResponseModule = {
  timestamp: Date,
  httpCode: number,
  status: string,
  message: string,
  data: Module
}