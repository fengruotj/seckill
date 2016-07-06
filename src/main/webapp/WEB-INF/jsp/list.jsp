
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<html>
<head>
    <title>秒杀列表页面</title>
    <%@include file="common/head.jsp"%>
</head>
<body>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>秒杀列表</h2>
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>名称</th>
                            <th>库存</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>创建时间</th>
                            <th>详情页面</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="seckill" items="${list}">
                            <tr>
                                <td>${seckill.name}</td>
                                <td>${seckill.number}</td>
                                <td><fmt:formatDate value="${seckill.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td><fmt:formatDate value="${seckill.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td><fmt:formatDate value="${seckill.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td><a class="btn btn-info" href="/seckill/${seckill.id}/detail">秒杀</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="/libs/jquery/jquery-2.0.0.min.js" charset="UTF-8"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="/libs/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
