# Github Action + AWS Elastic Beanstalk을 사용한 Spring boot CI/CD

AWS Elastic Beanstalk과 Github Action을 연동하고, CI/CD 구축을 마무리해보자.



### [ Elastic BeanStalk ]
Elastic Beanstalk은 EC2 인스턴스, 데이터베이스, 로드밸런서 등 여러 서비스를 포함한 환경을 구성하며 소프트웨어를 업데이트할 때마다 자동으로 환경을 관리해준다.

## 1. AWS Elastic Beanstalk 환경 생성
* 멀티 컨테이너 환경(Nginx, Spring Boot) 이므로 플랫폼 브랜치로 `ECS running on 64bit Amazon Linux2` 를 선택해야 한다.
* EB를 통해 트래픽의 양에 따라 인스턴스 개수를 조절하는 로드밸런싱을 이룰 수 있다.

<img src="https://user-images.githubusercontent.com/50009240/225657028-9759a253-04d4-4b6e-a4ac-740875614e10.png" width="700" height="600">

<img src="https://user-images.githubusercontent.com/50009240/225657163-f2f5ae4d-286c-45b9-9673-717c003657bc.png" width="700" height="500">


## 2. VPC와 Security Group 설정하기
AWS의 RDS(MySQL)을 어플리케이션과 연결해 통신하기 위해서는 VPC와 Security Group을 설정해주어야 한다. 

먼저 VPC와 Security Group의 개념부터 알아보자.



### [ VPC ]
Amazon Virtual Private Cloud(VPC)는 AWS 클라우드에서 논리적으로 격리된 공간을 제공하는 것이다. 이를 통해 고객이 정의한 가상 네트워크에서 AWS 리소스를 시작할 수 있다. 
아래와 같이 VPC가 같다고 해서 인스턴스(EC2, EB, RDS ..) 간의 통신이 가능한 것은 아니다. 통신 방법은 추후 설명할 예정이다.


아래와 같이 ElasticBeanstalk 인스턴스나 RDS 등을 생성하면 자동적으로 기본 VPC(default VPC)가 할당이 된다. 할당은 지역(region) 별로 다르게 할당된다. 






### [ Security Group ]
Security Group이란 inbound와 Outbound를 통제해서 트래픽을 열어줄 수도 있고, 닫아줄 수도 있다. 같은 VPC 내 AWS 서비스 간 통신을 위해선 같은 VPC에서 오는 트래픽을 모두 허용하도록 Security Group을 구성하면 된다.

Inbound : 외부에서 EC2 인스턴스나 EB 인스턴스로 요청을 보내는 트래픽 (HTTP, HTTPS, SSH ..)
Outbound : 인스턴스 (EC2, EB ..)에서 외부로 나가는 트래픽, 파일을 다운로드하거나 inbound로 들어온 트래픽을 처리하여 응답하는 경우도 포함된다. 

















[ DB ]
개발 환경에서는 Docker 컨테이너 환경을 이용할 것이고, 운영환경에서는 AWS RDS 서비스를 이용할 것이다. DB 작업은 중요한 데이터들을 보관하고, 이용하는 부분이기에 실제 데이터들을 다루는 운영 환경에서는 더욱 안정적인 AWS RDS를 이용해 DB를 구성하는 것이 좋다.
