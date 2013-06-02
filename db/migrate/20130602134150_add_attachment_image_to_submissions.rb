class AddAttachmentImageToSubmissions < ActiveRecord::Migration
  def self.up
    change_table :submissions do |t|
      t.attachment :image
    end
  end

  def self.down
    drop_attached_file :submissions, :image
  end
end
