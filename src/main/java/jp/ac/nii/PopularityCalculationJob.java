package jp.ac.nii;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * エリアごとの業種の出現数を計算するジョブのJobです。
 */
public class PopularityCalculationJob extends Job {

	// HDFS上の入力ファイル「エリア-カテゴリー」
	private static final Path inputFile = new Path(FilePathConstants.FILE_BASE
			+ "/" + FilePathConstants.AREA_CATEGORY_FILE_NAME);

	// HDFS上に出力されるファイル「エリア人気カテゴリー」
	private static final Path outputFile = new Path(FilePathConstants.FILE_BASE
			+ "/" + FilePathConstants.AREA_POPULARITY_FILE_NAME);

	public PopularityCalculationJob() throws IOException {
		this.setJobName("PopularityCalculationJob");
		this.setJarByClass(PopularityCalculationJob.class);

		// MapperクラスとReducerクラスを設定する
		this.setMapperClass(PopularityCalculationMapper.class);
		this.setReducerClass(PopularityCalculationReducer.class);

		// 中間データのKeyとValueの型を設定する
		this.setMapOutputKeyClass(Text.class);
		this.setMapOutputValueClass(IntWritable.class);
		this.setOutputKeyClass(NullWritable.class);
		this.setOutputValueClass(Text.class);

		// 利用するInputFormatとOutputFormatを設定する
		this.setInputFormatClass(TextInputFormat.class);
		this.setOutputFormatClass(TextOutputFormat.class);

		// HDFS上の入力ファイルと出力ファイルのパスを設定するコードを記載してください
		FileInputFormat.addInputPath(this, inputFile);
		FileOutputFormat.setOutputPath(this, outputFile);

		this.setNumReduceTasks(10);
	}
}
