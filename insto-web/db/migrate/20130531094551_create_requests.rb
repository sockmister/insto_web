class CreateRequests < ActiveRecord::Migration
  def change
    create_table :requests do |t|
      t.string :location

      t.timestamps
    end
  end
end
