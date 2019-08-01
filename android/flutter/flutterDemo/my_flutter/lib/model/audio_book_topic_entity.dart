class AudioBookTopicEntity {
	List<AudioBookTopicAdvertisemant> advertisement;
	List<AudioBookTopicTopic> topic;

	AudioBookTopicEntity({this.advertisement, this.topic});

	AudioBookTopicEntity.fromJson(Map<String, dynamic> json) {
		if (json['advertisement'] != null) {
			advertisement = new List<AudioBookTopicAdvertisemant>();(json['advertisement'] as List).forEach((v) { advertisement.add(new AudioBookTopicAdvertisemant.fromJson(v)); });
		}
		if (json['topic'] != null) {
			topic = new List<AudioBookTopicTopic>();(json['topic'] as List).forEach((v) { topic.add(new AudioBookTopicTopic.fromJson(v)); });
		}
	}

	Map<String, dynamic> toJson() {
		final Map<String, dynamic> data = new Map<String, dynamic>();
		if (this.advertisement != null) {
      data['advertisement'] =  this.advertisement.map((v) => v.toJson()).toList();
    }
		if (this.topic != null) {
      data['topic'] =  this.topic.map((v) => v.toJson()).toList();
    }
		return data;
	}
}

class AudioBookTopicAdvertisemant {
	String coverUrl;
	String jumpUrl;
	String name;
	int id;

	AudioBookTopicAdvertisemant({this.coverUrl, this.jumpUrl, this.name, this.id});

	AudioBookTopicAdvertisemant.fromJson(Map<String, dynamic> json) {
		coverUrl = json['cover_url'];
		jumpUrl = json['jump_url'];
		name = json['name'];
		id = json['id'];
	}

	Map<String, dynamic> toJson() {
		final Map<String, dynamic> data = new Map<String, dynamic>();
		data['cover_url'] = this.coverUrl;
		data['jump_url'] = this.jumpUrl;
		data['name'] = this.name;
		data['id'] = this.id;
		return data;
	}
}

class AudioBookTopicTopic {
	String coverUrl;
	String topicName;
	int id;

	AudioBookTopicTopic({this.coverUrl, this.topicName, this.id});

	AudioBookTopicTopic.fromJson(Map<String, dynamic> json) {
		coverUrl = json['cover_url'];
		topicName = json['topic_name'];
		id = json['id'];
	}

	Map<String, dynamic> toJson() {
		final Map<String, dynamic> data = new Map<String, dynamic>();
		data['cover_url'] = this.coverUrl;
		data['topic_name'] = this.topicName;
		data['id'] = this.id;
		return data;
	}
}
