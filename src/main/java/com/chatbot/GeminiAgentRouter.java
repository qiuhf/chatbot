// package com.chatbot;
//
// import com.google.gson.Gson;
// import com.google.gson.JsonObject;
//
// import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;
//
/// **
// * @author asus
// */
// public class GeminiAgentRouter {
// // Vertex AI 配置
// private static final String PROJECT_ID = "your-gcp-project-id";
// private static final String LOCATION = "us-central1";
// private static final String ENDPOINT = LOCATION + "-aiplatform.googleapis.com:443";
// private static final String ROUTER_MODEL = "gemini-1.5-flash";
// private static final String PRO_MODEL = "gemini-1.5-pro-latest";
//
// private final Gson gson = new Gson();
//
// // Agent 提示词模板
// private final Map<AgentType, String> agentPrompts = Map.of(
// AgentType.AIR_QUALITY,
// "你是一名空气质量专家，精通全球空气质量标准(WHO, EPA)。回答必须包含：\n" +
// "1. 污染物浓度数值及单位\n" +
// "2. AQI分级及对应健康影响\n" +
// "3. 敏感人群特别建议\n" +
// "4. 数据来源和时间（如已知）\n\n" +
// "用户问题：{question}",
//
// AgentType.WEATHER,
// "你是一名气象学家，请根据最新气象数据回答：\n" +
// "1. 当前温度、湿度、风速\n" +
// "2. 降水概率和降水量\n" +
// "3. 未来6小时趋势预测\n" +
// "4. 特殊天气预警（如有）\n\n" +
// "用户问题：{question}",
//
// AgentType.UV_RADIATION,
// "你是一名紫外线防护专家，回答需包含：\n" +
// "1. 当前紫外线指数及风险等级\n" +
// "2. 建议防晒措施（SPF值建议）\n" +
// "3. 安全暴露时长\n" +
// "4. 敏感人群特别提示\n\n" +
// "用户问题：{question}",
//
// AgentType.HEALTH_ADVICE,
// "你是一名环境健康顾问，针对用户问题提供：\n" +
// "1. 健康风险评估（分低/中/高/极高）\n" +
// "2. 具体防护措施\n" +
// "3. 敏感人群（老人、儿童、呼吸疾病患者）特别建议\n" +
// "4. 就医建议（如需要）\n\n" +
// "用户问题：{question}",
//
// AgentType.GENERAL,
// "你是一名环境科学助手，请专业解答以下问题：\n" +
// "{question}\n\n" +
// "回答需引用权威来源（如WHO、NASA、NOAA等）。"
// );
//
// // 调用 Vertex AI Gemini 模型
// private String callGeminiModel(String prompt, String modelId) throws IOException {
// // 设置终端节点
// PredictionServiceSettings settings = PredictionServiceSettings.newBuilder()
// .setEndpoint(ENDPOINT)
// .build();
//
// try (PredictionServiceClient client = PredictionServiceClient.create(settings)) {
// // 构建终端名称
// EndpointName endpointName = EndpointName.ofProjectLocationPublisherModelName(
// PROJECT_ID, LOCATION, "google", modelId);
//
// // 构建请求实例
// List<Value> instances = new ArrayList<>();
// Value.Builder instance = Value.newBuilder();
// JsonFormat.parser().merge("{ \"prompt\": \"" + escapeJson(prompt) + "\" }", instance);
// instances.add(instance.build());
//
// // 设置模型参数
// Value parameters = Value.newBuilder();
// JsonFormat.parser().merge("{"
// + "\"temperature\": 0.2,"
// + "\"maxOutputTokens\": 1024,"
// + "\"topP\": 0.8,"
// + "\"topK\": 40"
// + "}", parameters);
//
// // 构建预测请求
// PredictRequest predictRequest = PredictRequest.newBuilder()
// .setEndpoint(endpointName.toString())
// .addAllInstances(instances)
// .setParameters(parameters)
// .build();
//
// // 调用模型
// PredictResponse response = client.predict(predictRequest);
//
// // 解析响应
// for (Value prediction : response.getPredictionsList()) {
// JsonObject jsonResponse = gson.fromJson(
// JsonFormat.printer().print(prediction), JsonObject.class);
// if (jsonResponse.has("content")) {
// return jsonResponse.get("content").getAsString();
// }
// }
// }
// return "未能获取回答";
// }
//
// // 转义JSON特殊字符
// private String escapeJson(String input) {
// return input.replace("\\", "\\\\")
// .replace("\"", "\\\"")
// .replace("\n", "\\n")
// .replace("\r", "\\r")
// .replace("\t", "\\t");
// }
//
// // 选择最合适的Agent
// public AgentType selectAgent(String question) throws IOException {
// // 构建Agent描述JSON
// Map<String, String> agentDescriptions = new HashMap<>();
// for (AgentType agent : AgentType.values()) {
// agentDescriptions.put(agent.getTitle(), agent.getDescription());
// }
// String agentJson = gson.toJson(agentDescriptions);
//
// // 构建路由提示
// String routerPrompt = "请根据用户问题选择最合适的专业Agent类型。可用Agent:\n" +
// agentJson + "\n\n" +
// "用户问题: \"" + question + "\"\n\n" +
// "只需返回Agent类型名称（如\"空气质量专家\"），不要包含其他内容。";
//
// // 调用路由模型
// String selectedAgentName = callGeminiModel(routerPrompt, ROUTER_MODEL).trim();
//
// // 匹配Agent类型
// for (AgentType agent : AgentType.values()) {
// if (agent.getTitle().equals(selectedAgentName)) {
// return agent;
// }
// }
//
// // 默认返回通用Agent
// return AgentType.GENERAL;
// }
//
// // 提取地理位置信息
// public String extractLocation(String question) throws IOException {
// String locationPrompt = "从以下问题中提取地理位置信息，只需返回地点名称：\n" +
// "问题: \"" + question + "\"\n\n" +
// "示例:\n" +
// " 输入: \"北京今天的空气质量如何？\" → 输出: \"北京\"\n" +
// " 输入: \"我应该怎么防护紫外线？\" → 输出: \"未知\"";
//
// return callGeminiModel(locationPrompt, ROUTER_MODEL).trim();
// }
//
// // 模拟实时数据API调用
// public String getRealTimeData(AgentType agentType, String location) {
// // 实际应用中应替换为真实API调用
// switch (agentType) {
// case AIR_QUALITY:
// return "当前" + location + "的空气质量数据：AQI 65 (良), PM2.5: 25μg/m³";
// case WEATHER:
// return location + "实时天气：温度28°C, 湿度65%, 风速3m/s, 降水概率10%";
// case UV_RADIATION:
// return location + "紫外线指数：7 (高), 建议SPF30+防晒";
// case HEALTH_ADVICE:
// return "当前环境健康风险：中等";
// default:
// return "环境数据更新于2025-08-04";
// }
// }
//
// // 回答用户问题
// public String answerQuestion(String question) throws IOException {
// System.out.println("\n🧠 用户问题: " + question);
//
// // 1. 选择Agent
// AgentType agentType = selectAgent(question);
// System.out.println("🔄 选择Agent: " + agentType.getTitle());
//
// // 2. 提取位置
// String location = extractLocation(question);
// System.out.println("📍 检测位置: " + location);
//
// // 3. 获取实时数据
// String realTimeData = getRealTimeData(agentType, location);
//
// // 4. 构建完整提示
// String promptTemplate = agentPrompts.get(agentType);
// String fullPrompt = promptTemplate.replace("{question}", question) +
// "\n\n实时数据: " + realTimeData;
//
// // 5. 调用专业Agent模型
// String response = callGeminiModel(fullPrompt, PRO_MODEL);
// System.out.println("💡 回答: \n" + response);
//
// return response;
// }
//
// public static void main(String[] args) {
// try {
// GeminiAgentRouter agentSystem = new GeminiAgentRouter();
//
// // 测试不同问题类型
// String[] questions = {
// "北京今天的PM2.5浓度是多少？敏感人群需要注意什么？",
// "上海当前紫外线指数高吗？需要什么防晒措施？",
// "广州未来3小时会下雨吗？出门要带伞吗？",
// "我有哮喘，今天适合在纽约户外运动吗？",
// "解释一下臭氧污染的形成机制"
// };
//
// for (String question : questions) {
// agentSystem.answerQuestion(question);
// System.out.println("-".repeat(80));
// }
//
// } catch (IOException e) {
// System.err.println("发生错误: " + e.getMessage());
// e.printStackTrace();
// }
// }
// }
